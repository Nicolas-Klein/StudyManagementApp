package com.example.studymanagementapp

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.awaitCancellation





@Composable
fun PlaceholderSettings() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Hier entsteht Einstellungen!", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun PomodoroMainScreen() {
    var currentScreen by remember { mutableStateOf(PomodoroState.FOCUS) }
    var focusCycleCount by remember { mutableStateOf(0) }

    var timeLeftInMillis by remember { mutableStateOf(PomodoroConfig.FOCUS_TIME) }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isTimerRunning, key2 = currentScreen) {
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
                            timeLeftInMillis = PomodoroConfig.SHORT_BREAK_TIME
                        }
                    } else {
                        currentScreen = PomodoroState.FOCUS
                        timeLeftInMillis = PomodoroConfig.FOCUS_TIME
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Abgeschlossene Fokus-Phasen: $focusCycleCount",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 50.dp)
        )

        val viewModifier = Modifier.weight(1f).fillMaxWidth()
        val formattedTime = formatTime(timeLeftInMillis)


        when (currentScreen) {
            PomodoroState.FOCUS -> LearningTimerView(formattedTime, modifier = viewModifier)
            PomodoroState.SHORT_BREAK -> ShortBreakTimerView(formattedTime, modifier = viewModifier)
            PomodoroState.LONG_BREAK -> LongBreakTimerView(formattedTime, modifier = viewModifier)
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
                        PomodoroState.FOCUS -> PomodoroConfig.FOCUS_TIME
                        PomodoroState.SHORT_BREAK -> PomodoroConfig.FOCUS_TIME
                        PomodoroState.LONG_BREAK -> PomodoroConfig.FOCUS_TIME
                    }
                }
            ) {
                Text("Reset")
            }
        }
    }
}

