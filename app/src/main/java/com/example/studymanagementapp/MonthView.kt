package com.example.studymanagementapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class TaskDeadline(
    val id: Int,
    val title: String,
    val dueDate: String
)

@Composable
fun MonthView(innerPadding: PaddingValues, deadlineList: List<TaskDeadline>) {

    println("DeadlineList size: " + deadlineList.size)

    val sortedDeadlines = remember(deadlineList) {
        deadlineList.sortedBy { it.dueDate }
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
                DeadlineCard(deadline = deadline)
            }
        }

    }



}

@Composable
fun DeadlineCard(deadline: TaskDeadline) {
    val today = LocalDate.now()

    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val parsedDueDate = try {
        LocalDate.parse(deadline.dueDate, dateFormat)
    } catch (e: Exception) {
        today
    }

    val daysRemaining = ChronoUnit.DAYS.between(today, parsedDueDate)

    val badgeColor = when {
        daysRemaining < 0 -> MaterialTheme.colorScheme.error
        daysRemaining <= 3 -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.secondaryContainer
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = deadline.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fällig am: ${deadline.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = MaterialTheme.shapes.small,
                color = badgeColor,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = when {
                        daysRemaining < 0 -> "Überfällig!"
                        daysRemaining == 0L -> "Heute fällig!"
                        daysRemaining == 1L -> "Morgen!"
                        else -> "In $daysRemaining Tagen"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = contentColorFor(badgeColor)
                )
            }
        }
    }
}

