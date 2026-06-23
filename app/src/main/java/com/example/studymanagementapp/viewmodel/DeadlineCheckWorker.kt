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

class DeadlineCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

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