package com.example.studymanagementapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LearningTimerView(formattedTime: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Fokus-Phase", style = MaterialTheme.typography.headlineMedium)
            Text(text = formattedTime, style = MaterialTheme.typography.displayLarge)
        }
    }
}

@Composable
fun ShortBreakTimerView(formattedTime: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Kurze Pause", style = MaterialTheme.typography.headlineMedium)
            Text(text = formattedTime, style = MaterialTheme.typography.displayLarge)
        }
    }
}

@Composable
fun LongBreakTimerView(formattedTime: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Lange Pause", style = MaterialTheme.typography.headlineMedium)
            Text(text = formattedTime, style = MaterialTheme.typography.displayLarge)
        }
    }
}