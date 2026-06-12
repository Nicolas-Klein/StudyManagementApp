package com.example.studymanagementapp

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
import java.time.DayOfWeek
import java.time.LocalDate

data class TaskForDay(
    val id: Int,
    val title: String,
    val dayOfTask: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekView(innerPadding: PaddingValues, taskList: List<TaskForDay>) {

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
                Weekdays.MO -> WeekdayTaskCard("Montag", taskList)
                Weekdays.DI -> WeekdayTaskCard("Dienstag", taskList)
                Weekdays.MI -> WeekdayTaskCard("Mittwoch", taskList)
                Weekdays.DO -> WeekdayTaskCard("Donnerstag", taskList)
                Weekdays.FR -> WeekdayTaskCard("Freitag", taskList)
                Weekdays.SA -> WeekdayTaskCard("Samstag", taskList)
                Weekdays.SO -> WeekdayTaskCard("Sonntag", taskList)
            }
        }
    }
}

@Composable
fun WeekdayTaskCard(weekday: String, taskList: List<TaskForDay>) {





}