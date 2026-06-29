package com.example.studymanagementapp.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.studymanagementapp.R
import com.example.studymanagementapp.storage.StorageManager
import com.example.studymanagementapp.utils.calculateDaysRemaining
import java.time.LocalDate
import androidx.work.WorkManager
import com.example.studymanagementapp.data.TaskDeadline

/**
 * Ein Hintergrund-Dienst (Worker), der periodisch oder einmalig vom Android [WorkManager]
 * aufgerufen wird, um anstehende Fristen ([TaskDeadline]) zu überprüfen.
 *
 * Diese Klasse läuft entkoppelt von der Benutzeroberfläche im Hintergrund. Sie gleicht die
 * aktuellen Deadlines ab und löst bei Bedarf Systembenachrichtigungen (Notifications) aus,
 * um den Nutzer an fällige Aufgaben zu erinnern.
 *
 * @property context Der [Context] der Anwendung, der für den Zugriff auf den [StorageManager]
 * und das Benachrichtigungssystem benötigt wird.
 * @param workerParams Die vom [WorkManager] bereitgestellten Laufzeitparameter (z. B. Übergabedaten oder IDs).
 */
class DeadlineCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    /**
     * Die Kernmethode des Workers. Enthält die eigentliche Logik zur Überprüfung
     * der Deadlines. Wird automatisch auf einem Hintergrund-Thread ausgeführt.
     *
     * @return [Result.success] bei erfolgreicher Überprüfung, [Result.retry] bei temporären
     * Problemen oder [Result.failure], wenn ein kritischer Fehler aufgetreten ist.
     */
    override fun doWork(): Result {

        val storageManager = StorageManager(context)
        val deadlines = storageManager.loadDeadlines()

        for (deadline in deadlines) {
            try {
                val daysRemaining = calculateDaysRemaining(deadline.dueDate, LocalDate.now().toString())

                if(daysRemaining in 0..7L) {
                    sendDeadlineNotification(
                        context,
                        "Endspurt!",
                        "Deine Aufgabe '${deadline.title}' ist in $daysRemaining fällig!",
                        deadline.id
                    )
                }
            } catch (_: Exception) {

            }
        }

        return Result.success()

    }


    /**
     * Erstellt und sendet eine System-Benachrichtigung (Push-Notification), um den Nutzer
     * an eine bevorstehende Frist zu erinnern.
     *
     * Die Funktion sorgt im Hintergrund dafür, dass die Benachrichtigung an den entsprechenden
     * Notification-Channel übergeben wird, damit sie auch im Sperrbildschirm des Geräts erscheint.
     *
     * @param context Der [Context] der Anwendung, der für den Zugriff auf den [NotificationManager] benötigt wird.
     * @param title Die Überschrift der Benachrichtigung (z. B. der Name der Klausur oder Aufgabe).
     * @param message Der Detailtext der Benachrichtigung (z. B. "Abgabe in 2 Stunden!").
     * @param deadlineID Die eindeutige Kennung der Deadline.
     */
    fun sendDeadlineNotification(context: Context, title: String, message: String, deadlineID: Int) {
        val channelID = "deadline_notification"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelID,
            "Deadline Erinnerungen",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Benachrichtigt dich 7 Tage vor einer Deadline"
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(100 + deadlineID, notification)
    }
}