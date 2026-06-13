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
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.ui.components.WeekviewCards
import java.time.DayOfWeek
import java.time.LocalDate



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekView(innerPadding: PaddingValues, taskList: List<TaskForDay>, onDeleteTaskClick: (TaskForDay) -> Unit) {

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
                Weekdays.MO -> WeekviewCards("Montag", taskList, onDeleteTask = onDeleteTaskClick)
                Weekdays.DI -> WeekviewCards("Dienstag", taskList, onDeleteTask = onDeleteTaskClick)
                Weekdays.MI -> WeekviewCards("Mittwoch", taskList, onDeleteTask = onDeleteTaskClick)
                Weekdays.DO -> WeekviewCards("Donnerstag", taskList, onDeleteTask = onDeleteTaskClick)
                Weekdays.FR -> WeekviewCards("Freitag", taskList, onDeleteTask = onDeleteTaskClick)
                Weekdays.SA -> WeekviewCards("Samstag", taskList, onDeleteTask = onDeleteTaskClick)
                Weekdays.SO -> WeekviewCards("Sonntag", taskList, onDeleteTask = onDeleteTaskClick)
            }
        }
    }
}