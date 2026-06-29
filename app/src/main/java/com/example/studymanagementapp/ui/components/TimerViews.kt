package com.example.studymanagementapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.utils.PomodoroConfig.FOCUS_TIME_COLOR
import com.example.studymanagementapp.utils.PomodoroConfig.LONG_BREAK_COLOR
import com.example.studymanagementapp.utils.PomodoroConfig.SHORT_BREAK_COLOR

/**
 * Komponente zur Darstellung der Fokus-Phase des Pomodoro-Timer.
 *
 * @param formattedTime Enthält die runterlaufende Zeit des Timers die nach dem Format mm:ss als String
 * @param modifier Der [Modifier], mit dem Layout-Eigenschaften wie Abstände (Padding), Größen oder Ausrichtungen von der Übergeordneten Komponente angepasst werden können.
 */
@Composable
fun LearningTimerView(formattedTime: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .size(240.dp)
                .drawBehind{
                    drawCircle(
                        color = Color(FOCUS_TIME_COLOR),
                        style = Stroke(width = 8.dp.toPx())
                    )
            },
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Fokus-Phase", style = MaterialTheme.typography.headlineMedium)
            Text(text = formattedTime, style = MaterialTheme.typography.displayLarge)
        }
    }
}

/**
 * Komponente zur Darstellung der kurzen Pause-Phase des Pomodoro-Timer.
 *
 * @param formattedTime Enthält die runterlaufende Zeit des Timers die nach dem Format mm:ss als String
 * @param modifier Der [Modifier], mit dem Layout-Eigenschaften wie Abstände (Padding), Größen oder Ausrichtungen von der Übergeordneten Komponente angepasst werden können.
 */
@Composable
fun ShortBreakTimerView(formattedTime: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {

        Box(
            modifier = Modifier
                .size(240.dp)
                .drawBehind{
                    drawCircle(
                        color = Color(SHORT_BREAK_COLOR),
                        style = Stroke(width = 8.dp.toPx())
                    )
                },
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Kurze Pause", style = MaterialTheme.typography.headlineMedium)
            Text(text = formattedTime, style = MaterialTheme.typography.displayLarge)
        }
    }
}

/**
 * Komponente zur Darstellung der langen Pause-Phase des Pomodoro-Timer.
 *
 * @param formattedTime Enthält die runterlaufende Zeit des Timers die nach dem Format mm:ss als String
 * @param modifier Der [Modifier], mit dem Layout-Eigenschaften wie Abstände (Padding), Größen oder Ausrichtungen von der Übergeordneten Komponente angepasst werden können.
 */
@Composable
fun LongBreakTimerView(formattedTime: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .drawBehind{
                    drawCircle(
                        color = Color(LONG_BREAK_COLOR),
                        style = Stroke(width = 8.dp.toPx())
                    )
                },
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Lange Pause", style = MaterialTheme.typography.headlineMedium)
            Text(text = formattedTime, style = MaterialTheme.typography.displayLarge)
        }
    }
}