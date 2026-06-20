package com.example.studymanagementapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.utils.filterTasksByDay

@Composable
fun WeekviewCards(weekday: String, taskList: List<TaskForDay>, deadlineList: List<TaskDeadline>, onDeleteTask: (TaskForDay) -> Unit, onSelectTask: (TaskForDay) -> Unit) {

    val tasksForThisDay = filterTasksByDay(taskList, weekday)

    val state = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp).
            verticalScroll(state)
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

                val linkedDeadline = remember(task.linkedDeadline, deadlineList) {
                    deadlineList.find { it.id == task.linkedDeadline }
                }

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

                        task.linkedDeadline?.let {
                            Text(
                                text = linkedDeadline?.title ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        IconButton(onClick = { onSelectTask(task) }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircleOutline,
                                contentDescription = "Aufgabe auswählen",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(onClick = { onDeleteTask(task) }) {
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