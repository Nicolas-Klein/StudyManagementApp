package com.example.studymanagementapp.utils

import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.utils.PomodoroConfig.FOCUS_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.SHORT_BREAK_TIME
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

fun calculateDaysRemaining(dueDate: String, anotherDay: String): Long {

    val today = LocalDate.now()

    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val parsedDueDate = try {
        LocalDate.parse(dueDate, dateFormat)
    } catch (_: Exception) {
        today
    }
    val parsedAnotherDay = try {
        LocalDate.parse(anotherDay, dateFormat)
    } catch (_: Exception) {
        today
    }


    return ChronoUnit.DAYS.between(parsedAnotherDay, parsedDueDate)
}

fun filterTasksByDay(taskList: List<TaskForDay>, weekday: String): List<TaskForDay> {
    return taskList.filter { it.dayOfTask.equals(weekday, ignoreCase = true) }
}

fun calculateTimerInterval(deadline: TaskDeadline?): Pair<Long, Long> {

    if(deadline == null) return Pair(FOCUS_TIME, SHORT_BREAK_TIME)

    return try {

        //val daysRemaining = calculateDaysRemaining(deadline.dueDate, taskDate.toString())

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val deadlineDate = LocalDate.parse(deadline.dueDate, formatter)

        val today = LocalDate.now()

        val daysRemaining = ChronoUnit.DAYS.between(today, deadlineDate)

        println("DEBUG_DENKFEHLER_FIX:")
        println("-> Heute ist: $today")
        println("-> Die Deadline ist am: $deadlineDate")
        println("-> Tage verbleibend ab HEUTE: $daysRemaining")

        if (daysRemaining in 0..7) {
            println("DEBUG_INTERVAL: Endspurt! Schalte um auf 50/10 ${deadline.dueDate}")

            val urgentFocus = 50 * 60 * 1000
            val urgentBreak = 10 * 60 * 1000
            Pair(urgentFocus, urgentBreak)

        } else {
            println("DEBUG_INTERVAL: Genug Zeit. Nutze Config-Standardwerte.")
            Pair(FOCUS_TIME, SHORT_BREAK_TIME)
        }

    } catch (e: Exception) {
        println("DEBUG_INTERVAL: Fehler beim Parsen: ${e.message}. Fallback auf Config")
        Pair(FOCUS_TIME, SHORT_BREAK_TIME)
    } as Pair<Long, Long>
}