package com.example.studymanagementapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.ui.components.MonthviewCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MonthView(innerPadding: PaddingValues, deadlineList: List<TaskDeadline>, onDeleteDeadlineClick: (TaskDeadline) -> Unit) {

    val sortedDeadlines = remember(deadlineList) {

        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        deadlineList.sortedBy { task ->
            try {
                LocalDate.parse(task.dueDate, inputFormatter)
            } catch (_: Exception) {
                LocalDate.MAX
            }
        }.map { task ->
            try {
                val parsedDate = LocalDate.parse(task.dueDate, inputFormatter)
                val formattedDate = parsedDate.format(outputFormatter)

                task.copy(dueDate = formattedDate)
            } catch (_: Exception) {
                task
            }
        }

    }

    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        Text(
            text = "Anstehende Fristen und Klausuren",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(sortedDeadlines, key = { it.id }) { deadline ->
                MonthviewCard(deadline = deadline, onDeleteDeadline = onDeleteDeadlineClick)
            }
        }

    }



}



