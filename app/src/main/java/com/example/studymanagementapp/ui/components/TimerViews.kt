package com.example.studymanagementapp.ui.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

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
                        color = Color(0xFFE57373),
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
                        color = Color(0xFF81C784),
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
                        color = Color(0xFF64B5F6),
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