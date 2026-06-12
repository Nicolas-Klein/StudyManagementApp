package com.example.studymanagementapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    for (i in taskList) {
        println(i.toString())
    }

    val tasksForThisDay = taskList.filter { it.dayOfTask.equals(weekday, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = weekday,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if(tasksForThisDay.isEmpty()) {
            Text(
                text = "Keine Aufgaben für diesen Tag",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
        } else {
            tasksForThisDay.forEach { task ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(onClick = { /*Delete Tast*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Aufgabe löschen",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}