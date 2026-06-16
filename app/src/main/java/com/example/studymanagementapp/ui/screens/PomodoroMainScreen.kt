package com.example.studymanagementapp.ui.screens

import android.os.CountDownTimer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.storage.StorageManager
import com.example.studymanagementapp.utils.PomodoroConfig
import com.example.studymanagementapp.ui.components.LearningTimerView
import com.example.studymanagementapp.ui.components.LongBreakTimerView
import com.example.studymanagementapp.ui.components.ShortBreakTimerView
import com.example.studymanagementapp.utils.calculateTimerInterval
import com.example.studymanagementapp.utils.formatTime
import kotlinx.coroutines.awaitCancellation
import com.example.studymanagementapp.utils.PomodoroConfig.FOCUS_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.SHORT_BREAK_TIME

enum class PomodoroState {
    FOCUS,
    SHORT_BREAK,
    LONG_BREAK
}

@Composable
fun PomodoroMainScreen(
    initialTaskName: Int?,
    initialDeadlineName: Int?,
) {
    val context = LocalContext.current

    val storageManager = remember { StorageManager(context = context) }

    var currentFocusedTask by remember { mutableStateOf<TaskForDay?>(null) }
    var currentFocusedDeadline by remember { mutableStateOf<TaskDeadline?>(null) }

    var totalFocusTime by remember { mutableStateOf(FOCUS_TIME) }
    var totalBreakTime by remember { mutableStateOf(SHORT_BREAK_TIME) }

    var currentScreen by remember { mutableStateOf(PomodoroState.FOCUS) }
    var focusCycleCount by remember { mutableStateOf(0) }

    var timeLeftInMillis by remember { mutableStateOf(FOCUS_TIME) }
    var isTimerRunning by remember { mutableStateOf(false) }

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
        totalFocusTime = calculatedFocus
        totalBreakTime = calculateBreak

        timeLeftInMillis = totalFocusTime
    }



    LaunchedEffect(isTimerRunning, currentScreen, totalFocusTime, totalBreakTime) {

        if (!isTimerRunning) {
            timeLeftInMillis = when (currentScreen) {
                PomodoroState.FOCUS -> totalFocusTime
                PomodoroState.SHORT_BREAK -> totalBreakTime
                PomodoroState.LONG_BREAK -> PomodoroConfig.LONG_BREAK_TIME
            }
        }

        if (isTimerRunning){
            val timer = object : CountDownTimer(timeLeftInMillis, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                }

                override fun onFinish() {
                    isTimerRunning = false

                    if (currentScreen == PomodoroState.FOCUS) {
                        focusCycleCount++

                        if (focusCycleCount % 4 == 0) {
                            currentScreen = PomodoroState.LONG_BREAK
                            timeLeftInMillis = PomodoroConfig.LONG_BREAK_TIME
                        } else {
                            currentScreen = PomodoroState.SHORT_BREAK
                            timeLeftInMillis = totalBreakTime
                        }
                    } else {
                        currentScreen = PomodoroState.FOCUS
                        timeLeftInMillis = totalFocusTime
                    }
                }
            }.start()

            try {
                awaitCancellation()
            } finally {
                timer.cancel()
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Abgeschlossene Fokus-Phasen: $focusCycleCount",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 5.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        val viewModifier = Modifier.weight(1f).fillMaxWidth()
        val formattedTime = formatTime(timeLeftInMillis)


        when (currentScreen) {
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
                    style = MaterialTheme.typography.headlineMedium
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
                    isTimerRunning = !isTimerRunning
                },
            ) {
                Text(
                    text = if (isTimerRunning) "Pause" else "Start"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    isTimerRunning = false
                    timeLeftInMillis = when (currentScreen){
                        PomodoroState.FOCUS -> totalFocusTime
                        PomodoroState.SHORT_BREAK -> totalBreakTime
                        PomodoroState.LONG_BREAK -> PomodoroConfig.LONG_BREAK_TIME
                    }
                }
            ) {
                Text("Reset")
            }
        }
    }
}

