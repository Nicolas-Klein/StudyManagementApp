package com.example.studymanagementapp.utils

import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.utils.PomodoroConfig.FOCUS_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.SHORT_BREAK_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.URGENT_FOCUS_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.URGENT_SHORT_BREAK_TIME
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import com.example.studymanagementapp.ui.components.DeadlineViewCard

/**
 * Konvertiert eine Zeitdauer von Millisekunden in einen lesbaren formatierten String.
 * Berechnet die Minuten und Sekunden aus den übergebene Millisekungen und formatiert sie in dem Muster "mm:ss" (z. B. wird 65000 ms zu "01:05").
 * Diese funktion wird für die Zeitanzeige der Pomodoro-Timer-Phasen verwendet.
 */
fun formatTime(millis: Long) : String {
    val minutes = (millis / 1000) / 60
    val seconds = (millis / 1000) % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

/**
 * Konvertiert einen Zeitstempel in Millisekunden in einen formatierten Datums-String.
 *
 * Diese Hilfsfunktion wird primär verwendet, um Rohdaten aus Zeitstempeln (z. B. von einem DatePicker) in ein benutzerfreundliches Format für die UI umzuwandeln.
 *
 * @param millis Der umzuwandelnde Zeitstempel in Millisekunden seit der Epoche (01.01.1970). Kann null sein, falls kein Datum ausgewählt oder vorhanden ist.
 *
 * @return Das formatierte Datum als String (z. B. "29.06.2026").
 */
fun formatMillisToDateString(millis: Long?): String {
    if (millis == null) return ""
    val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return localDate.format(formatter)
}

/**
 * Berechnet die verbleibenden Tage zwischen einem festgelegten Fälligkeitsdatum und einem zweiten Vergleichsdatum.
 *
 * Die Hilfsfunktion wird in [DeadlineViewCard] verwendet um die verbleibenden Tage bis zu einer Deadline zu berechnen.
 *
 * @param dueDate Das Fälligkeitsdatum der Deadline im Format "dd.MM.yyyy".
 * @param anotherDay Das Vergleichsdatum (in der Regel das aktuelle Tagesdatum) im Format "dd.MM.yyyy".
 * @return Die Anzahl der verbleibenden Tage als [Long]. Der Wert ist negativ, wenn das Fälligkeitsdatum bereits in der Vergangenheit liegt.
 * @throws java.time.format.DateTimeParseException Falls eines der Daten nicht dem erwarteten Format entspricht.
 */
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

/**
 * Filtert eine Gesamtliste von Aufgaben nach einem bestimmten Wochentag.
 *
 * Diese Hilfsfunktion extrahiert alle Einträge aus der [taskList], deren zugewiesener Wochentag exakt mit dem übergebenen [weekday] übereinstimmt.
 *
 * @param taskList Die Ausgangsliste aller im System vorhandenen Aufgaben ([TaskForDay]).
 * @param weekday Der Name des Wochentags, nach dem gefiltert werden soll (z. B. "Montag").
 * @return Eine neue Liste, die nur noch die [TaskForDay]-Objekte des angeforderten Wochentags enthält. Ist für den Tag keine Aufgabe vorhanden, wird eine leere Liste zurückgegeben.
 */
fun filterTasksByDay(taskList: List<TaskForDay>, weekday: String): List<TaskForDay> {
    return taskList.filter { it.dayOfTask.equals(weekday, ignoreCase = true) }
}

/**
 * Berechnet das verbleibende Zeitintervall bis zum Erreichen einer bestimmten Frist.
 *
 * Die Funktion ermittelt die Differenz zwischen der aktuellen Systemzeit und dem
 * Fälligkeitsdatum der übergebenen Deadline. Sie wird verwendet, um Countdown-Timer
 * oder Hintergrund-Worker präzise zu steuern.
 *
 * @param deadline Das [TaskDeadline]-Objekt, dessen Fälligkeit berechnet werden soll.
 * Kann null sein, falls aktuell keine aktive Frist gesetzt ist.
 *
 * @return Ein [Pair] bestehend aus zwei [Long]-Werten:
 * - **first**: Die Zeit der Fokus-Timer-Phase in millisekunden
 * - **second**: Die Zeit der kurzen Pause-Timer-Phase in millisekunden
 */
fun calculateTimerInterval(deadline: TaskDeadline?): Pair<Long, Long> {

    if(deadline == null) return Pair(FOCUS_TIME, SHORT_BREAK_TIME)

    return try {

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val deadlineDate = LocalDate.parse(deadline.dueDate, formatter)

        val today = LocalDate.now()

        val daysRemaining = ChronoUnit.DAYS.between(today, deadlineDate)

        if (daysRemaining in 0..7) {
            println("DEBUG_INTERVAL: Endspurt! Schalte um auf 50/10 ${deadline.dueDate}")

            Pair(URGENT_FOCUS_TIME, URGENT_SHORT_BREAK_TIME)

        } else {
            println("DEBUG_INTERVAL: Genug Zeit. Nutze Config-Standardwerte.")
            println("DEBUG_INTERVAL: ${deadline.dueDate}.")
            Pair(FOCUS_TIME, SHORT_BREAK_TIME)
        }

    } catch (e: Exception) {
        println("DEBUG_INTERVAL: Fehler beim Parsen: ${e.message}. Fallback auf Config")
        Pair(FOCUS_TIME, SHORT_BREAK_TIME)
    }
}