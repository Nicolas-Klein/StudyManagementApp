package com.example.studymanagementapp.utils

import com.example.studymanagementapp.data.TaskForDay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun formatTime(millis: Long) : String {
    val minutes = (millis / 1000) / 60
    val seconds = (millis / 1000) % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

fun formatMillisToDateString(millis: Long?): String {
    if (millis == null) return ""
    val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return localDate.format(formatter)
}

fun calculateDaysRemaining(dueDate: String): Long {
    val today = LocalDate.now()

    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val parsedDueDate = try {
        LocalDate.parse(dueDate, dateFormat)
    } catch (e: Exception) {
        today
    }

    return ChronoUnit.DAYS.between(today, parsedDueDate)
}

fun filterTasksByDay(taskList: List<TaskForDay>, weekday: String): List<TaskForDay> {
    return taskList.filter { it.dayOfTask.equals(weekday, ignoreCase = true) }
}