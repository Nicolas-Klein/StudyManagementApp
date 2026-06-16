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

enum class Weekdays {
    MO,
    DI,
    MI,
    DO,
    FR,
    SA,
    SO
}

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