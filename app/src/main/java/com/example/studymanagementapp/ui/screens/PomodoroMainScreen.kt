package com.example.studymanagementapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.storage.StorageManager
import com.example.studymanagementapp.ui.components.LearningTimerView
import com.example.studymanagementapp.ui.components.LongBreakTimerView
import com.example.studymanagementapp.ui.components.ShortBreakTimerView
import com.example.studymanagementapp.utils.calculateTimerInterval
import com.example.studymanagementapp.utils.formatTime
import com.example.studymanagementapp.data.PomodoroState
import com.example.studymanagementapp.viewmodel.TimerViewModel

// Test Notification

/**
 * Der Container, der die verschiednen Timer-Phasen des Pomodoro-Timers darstellt.
 *
 * @param initialTaskName Zeigt den Namen der vom Nutzer ausgewählte Fokus-Aufgabe. Default ist "Bereit für den Fokus?"
 * @param initialDeadlineName Enthält die optinale Deadline von der vom Nutzer ausgewählten Fokus-Aufgabe.
 * @param viewModel ViewModel, dass die Logik des Timers beinhaltet, darunter das herunterzählen, starten, stoppen und das wechseln der Timer-Phasen.
 */
@Composable
fun PomodoroMainScreen(
    initialTaskName: Int?,
    initialDeadlineName: Int?,
    viewModel: TimerViewModel
) {
    val context = LocalContext.current

    val storageManager = remember { StorageManager(context = context) }

    var currentFocusedTask by remember { mutableStateOf<TaskForDay?>(null) }
    var currentFocusedDeadline by remember { mutableStateOf<TaskDeadline?>(null) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(initialTaskName, initialDeadlineName) {
        if (initialTaskName != null) {
            val allTasks = storageManager.loadTodoTasks()
            currentFocusedTask = allTasks.find { it.id == initialTaskName }
        } else {
            currentFocusedTask = null
        }

        if (initialDeadlineName != null) {
            val allDeadlines = storageManager.loadDeadlines()
            currentFocusedDeadline = allDeadlines.find { it.id == initialDeadlineName }
        } else {
            currentFocusedTask = null
        }

        val (calculatedFocus, calculateBreak) = calculateTimerInterval(currentFocusedDeadline)

        viewModel.setTimerDuration(focusMillis = calculatedFocus, breakMillis = calculateBreak)

    }

    if(isLandscape) {
        Landscape(
            viewModel = viewModel,
            currentFocusedTask = currentFocusedTask
        )
    } else {
        NotLandscape(
            viewModel = viewModel,
            currentFocusedTask = currentFocusedTask
        )
    }


}


@Composable
fun NotLandscape(viewModel: TimerViewModel, currentFocusedTask: TaskForDay?) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (viewModel.currentScreen) {
                PomodoroState.FOCUS -> "Fokus-Phase (${viewModel.focusCycleCount})"
                PomodoroState.SHORT_BREAK -> "Kurze Pause"
                PomodoroState.LONG_BREAK -> "Lange erholungspause"
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 5.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        val viewModifier = Modifier.weight(1f).fillMaxWidth()
        val formattedTime = formatTime(viewModel.timeLeftInMillis)


        when (viewModel.currentScreen) {
            PomodoroState.FOCUS -> LearningTimerView(formattedTime, modifier = viewModifier)
            PomodoroState.SHORT_BREAK -> ShortBreakTimerView(formattedTime, modifier = viewModifier)
            PomodoroState.LONG_BREAK -> LongBreakTimerView(formattedTime, modifier = viewModifier)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(currentFocusedTask != null) {
                Text(
                    text = "Fokus auf:\n${currentFocusedTask!!.title}",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "Bereit für den Fokus?",
                    style= MaterialTheme.typography.headlineMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.padding(bottom = 32.dp)) {
            Button(
                onClick = {
                    if(viewModel.isTimerRunning) viewModel.pauseTimer() else viewModel.startTimer()
                },
            ) {
                Text(
                    text = if (viewModel.isTimerRunning) "Pause" else "Start"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    viewModel.resetTimer()
                    viewModel.stopAlarm()
                }
            ) {
                Text("Reset")
            }

            Spacer(modifier = Modifier.width(16.dp))

            /*
            Button(
                onClick = {
                    // Test Notification
                    val testWorkRequest = OneTimeWorkRequestBuilder<DeadlineCheckWorker>().build()
                    WorkManager.getInstance(context).enqueue(testWorkRequest)

                    println("DEBUG_WORKER: Worker test abgeschlossen")
                }
            ) {
                Text("Test Notification")
            }
            */
        }
    }
}


@Composable
fun Landscape(viewModel: TimerViewModel, currentFocusedTask: TaskForDay?) {
    Row(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val viewModifier = Modifier
        val formattedTime = formatTime(viewModel.timeLeftInMillis)

        when (viewModel.currentScreen) {
            PomodoroState.FOCUS -> LearningTimerView(formattedTime, modifier = viewModifier)
            PomodoroState.SHORT_BREAK -> ShortBreakTimerView(formattedTime, modifier = viewModifier)
            PomodoroState.LONG_BREAK -> LongBreakTimerView(formattedTime, modifier = viewModifier)
        }

        Spacer(modifier = Modifier.width(64.dp))

        Column(
            modifier = Modifier
                .padding(1.dp),
        ) {

            Text(
                text = when (viewModel.currentScreen) {
                    PomodoroState.FOCUS -> "Fokus-Phase (${viewModel.focusCycleCount})"
                    PomodoroState.SHORT_BREAK -> "Kurze Pause"
                    PomodoroState.LONG_BREAK -> "Lange erholungspause"
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 5.dp)
            )

            if(currentFocusedTask != null) {
                Text(
                    text = "Fokus auf:\n${currentFocusedTask!!.title}",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "Bereit für den Fokus?",
                    style= MaterialTheme.typography.headlineMedium
                )
            }

            Row(modifier = Modifier.padding(bottom = 32.dp)) {
                Button(
                    onClick = {
                        if(viewModel.isTimerRunning) viewModel.pauseTimer() else viewModel.startTimer()
                    },
                ) {
                    Text(
                        text = if (viewModel.isTimerRunning) "Pause" else "Start"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        viewModel.resetTimer()
                        viewModel.stopAlarm()
                    }
                ) {
                    Text("Reset")
                }

                Spacer(modifier = Modifier.width(16.dp))

                /*
                Button(
                    onClick = {
                        // Test Notification
                        val testWorkRequest = OneTimeWorkRequestBuilder<DeadlineCheckWorker>().build()
                        WorkManager.getInstance(context).enqueue(testWorkRequest)

                        println("DEBUG_WORKER: Worker test abgeschlossen")
                    }
                ) {
                    Text("Test Notification")
                }
                */
            }
        }
    }
}