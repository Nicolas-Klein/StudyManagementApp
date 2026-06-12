package com.example.studymanagementapp

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit

data class TaskDeadline(
    val id: Int,
    val title: String,
    val dueDate: LocalDate
)

@Composable
fun MonthView(innerPadding: PaddingValues) {

    val deadlines by remember {
        mutableStateOf(
            listOf(
                TaskDeadline(1, "Titel 1", LocalDate.now()),
                TaskDeadline(2, "Titel 2", LocalDate.now().plusDays(1)),
                TaskDeadline(3, "Titel 3", LocalDate.now().plusDays(2)),
                TaskDeadline(4, "Titel 4", LocalDate.now().plusDays(3))
            )
        )
    }

    val sortedDeadlines = remember(deadlines) {
        deadlines.sortedBy { it.dueDate }
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

    val daysRemaining = ChronoUnit.DAYS.between(today, deadline.dueDate)

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

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
                    text = "Fällig am: ${deadline.dueDate.format(dateFormatter)}",
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

