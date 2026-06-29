package com.example.studymanagementapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.ui.components.WeekviewCards
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Beinhaltet die sieben Wochentage für das erstellen der Tabs des Wochenplaners.
 */
enum class Weekdays {
    /**
     * Repräsentiert Montag in kurz Schreibform
     */
    MO,
    /**
     * Repräsentiert Dienstag in kurz Schreibform
     */
    DI,
    /**
     * Repräsentiert Mittwoch in kurz Schreibform
     */
    MI,
    /**
     * Repräsentiert Donnerstag in kurz Schreibform
     */
    DO,
    /**
     * Repräsentiert Freitag in kurz Schreibform
     */
    FR,
    /**
     * Repräsentiert Samstag in kurz Schreibform
     */
    SA,
    /**
     * Repräsentiert Sonntag in kurz Schreibform
     */
    SO
}

/**
 * Hauptkomponente für die Wochenübersicht (Wochenansicht) des Studienplaners.
 *
 * Der Wochenplaner besteht aus sieben Tabs, welche die Wochentage repräsentieren und die Aufgaben ([TaskForDay]) der jeweiligen Tage beinhalten.
 *
 * @param innerPadding Die vom übergeordneten Scaffold bereitgestellten [PaddingValues] (wichtig für die korrekte Platzierung unter Top- und Bottom-Bars).
 * @param taskList Die aktuelle Liste aller für die Woche geplanten Aufgaben ([TaskForDay]).
 * @param deadlineList Die Liste aller anstehenden Abgabefristen ([TaskDeadline]), die optinal Aufgaben zugewiesen wurden.
 * @param onDeleteTaskClick Event-Callback, das ausgelöst wird, wenn der Nutzer eine Aufgabe löschen möchte. Übergibt das betroffene [TaskForDay]-Objekt.
 * @param onSelectTaskClick Event-Callback, das ausgelöst wird, wenn eine Aufgabe als aktuelle Fokus-Aufgabe ausgewählt wird.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekView(innerPadding: PaddingValues, taskList: List<TaskForDay>, deadlineList: List<TaskDeadline>, onDeleteTaskClick: (TaskForDay) -> Unit, onSelectTaskClick: (TaskForDay) -> Unit) {

    println("TaskList size: " + taskList.size)

    val dayOfWeek = LocalDate.now().dayOfWeek

    val weekday = when(dayOfWeek){
        DayOfWeek.MONDAY -> Weekdays.MO
        DayOfWeek.TUESDAY -> Weekdays.DI
        DayOfWeek.WEDNESDAY -> Weekdays.MI
        DayOfWeek.THURSDAY -> Weekdays.DO
        DayOfWeek.FRIDAY -> Weekdays.FR
        DayOfWeek.SATURDAY -> Weekdays.SA
        DayOfWeek.SUNDAY -> Weekdays.SO
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        var state by remember { mutableStateOf(weekday) }
        Column {
            SecondaryTabRow(selectedTabIndex = state.ordinal) {
                Weekdays.entries.forEachIndexed { index, title ->
                    Tab(
                        selected = state.ordinal == index,
                        onClick = { state = title },
                        text = { Text(text = title.toString()) },
                    )
                }
            }

            when(state) {
                Weekdays.MO -> WeekviewCards("Montag", taskList, deadlineList,  onDeleteTask = onDeleteTaskClick, onSelectTask = onSelectTaskClick)
                Weekdays.DI -> WeekviewCards("Dienstag", taskList, deadlineList, onDeleteTask = onDeleteTaskClick, onSelectTask = onSelectTaskClick)
                Weekdays.MI -> WeekviewCards("Mittwoch", taskList, deadlineList, onDeleteTask = onDeleteTaskClick, onSelectTask = onSelectTaskClick)
                Weekdays.DO -> WeekviewCards("Donnerstag", taskList, deadlineList, onDeleteTask = onDeleteTaskClick, onSelectTask = onSelectTaskClick)
                Weekdays.FR -> WeekviewCards("Freitag", taskList, deadlineList, onDeleteTask = onDeleteTaskClick, onSelectTask = onSelectTaskClick)
                Weekdays.SA -> WeekviewCards("Samstag", taskList, deadlineList, onDeleteTask = onDeleteTaskClick, onSelectTask = onSelectTaskClick)
                Weekdays.SO -> WeekviewCards("Sonntag", taskList, deadlineList, onDeleteTask = onDeleteTaskClick, onSelectTask = onSelectTaskClick)
            }
        }
    }
}