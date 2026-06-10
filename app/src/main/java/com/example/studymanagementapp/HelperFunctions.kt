package com.example.studymanagementapp

import java.util.Locale

fun formatTime(millis: Long) : String {
    val minutes = (millis / 1000) / 60
    val seconds = (millis / 1000) % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}