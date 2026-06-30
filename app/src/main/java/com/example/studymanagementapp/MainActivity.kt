package com.example.studymanagementapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.studymanagementapp.data.NavigationTarget
import com.example.studymanagementapp.ui.components.SharedBottomNavigationBar
import com.example.studymanagementapp.ui.components.SharedTopBar
import com.example.studymanagementapp.ui.screens.PomodoroMainScreen
import com.example.studymanagementapp.ui.theme.StudyManagementAppTheme
import com.example.studymanagementapp.viewmodel.DeadlineCheckWorker
import com.example.studymanagementapp.viewmodel.TimerViewModel
import java.util.concurrent.TimeUnit
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.data.TaskDeadline


/**
 * Der primäre Einstiegspunkt (Main Entry Point) der Anwendung.
 *
 * Diese Activity initialisiert beim Start das UI-System des Timer, als auch die Bottom Navigation Bar, um zwischen den Activities zu navigieren (Pomodoro-Timer und Lernplaner).
 */
class MainActivity : ComponentActivity() {

    private var taskIdState by mutableStateOf<Int?>(null)
    private var deadlineIdState by mutableStateOf<Int?>(null)

    private val timerViewModel by viewModels<TimerViewModel>()


    /**
     * Wird aufgerufen, wenn die Activity das erste Mal gestartet wird.
     *
     * Diese Methode übernimmt die grundlegende Initialisierung der Activity, wie das definieren von Permissions, erstellen des [DeadlineCheckWorker] und Festlegen des visuellen Layouts mittels [setContent] als auch das Theme des Pomodoro-Timers.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if(isGranted){

            }
        }

        if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DeadlineCheckWorker>(
            24,
            TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "DailyDeadlineCheck",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )

        extractIdsFromIntent(intent)

        enableEdgeToEdge()
        setContent {
            StudyManagementAppTheme {
                Scaffold(
                    bottomBar = { SharedBottomNavigationBar(currentScreen = NavigationTarget.TIMER) },
                    topBar = { SharedTopBar("Fokus-Timer") }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        PomodoroMainScreen(
                            initialTaskName = taskIdState,
                            initialDeadlineName = deadlineIdState,
                            viewModel = timerViewModel
                        )
                    }

                }
            }
        }
    }

    /**
     * Wird am Ende des Komponentent-Lifecycle aufgerufen.
     */
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun finish() {
        super.finish()

        overrideActivityTransition(
            OVERRIDE_TRANSITION_CLOSE,
            R.anim.fade_in,
            R.anim.fade_out
        )
    }

    /**
     * Wird vom Android-System aufgerufen, wenn die Aktivität gestartet wird, während sie bereits im Hintergrund aktiv war.
     *
     * Diese Methode fängt neue [Intent]-Signale ab, wenn der User beispielsweise eine Aufgabe als Fokus-Aufgabe setzt und diese Informationen von der MainActivity verarbeitet werden.
     *
     * @param intent Der neu eingegangene [Intent], der die Auslösedaten oder Extras enthält.
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        extractIdsFromIntent(intent)
    }

    /**
     * Extrahiert die IDs der Übergebenen Aufgabe ([TaskForDay]) und Deadline ([TaskDeadline]).
     *
     * Diese Hilfsfunktion wird von der Funktion [onNewIntent] aufgerufen um die relevanten Informationen aus dem Intent zu lesen.
     *
     * @param intent Der [Intent], der beim Starten der Activity übergeben wurde. Kann null sein, wenn die App ganz normal gestartet wurde.
     */
    private fun extractIdsFromIntent(intent: Intent?) {

        if(intent == null) {
            println("DEBUG_TIMER: Intent ist null")
            return
        }

        val taskId = intent.getIntExtra("EXTRA_TASK_ID", -1)
        val deadlineId = intent.getIntExtra("EXTRA_DEADLINE_ID", -1)

        println("DEBUG_TIMER: Ausgelesene Task-ID = ${taskId}, Deadline-ID = $deadlineId")

        taskIdState = if (taskId != -1) taskId else null
        deadlineIdState = if (deadlineId != -1) deadlineId else null

    }
}


