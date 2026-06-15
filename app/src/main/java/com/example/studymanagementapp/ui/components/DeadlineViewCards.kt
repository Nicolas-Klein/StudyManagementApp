package com.example.studymanagementapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.utils.calculateDaysRemaining

@Composable
fun DeadlineViewCard(deadline: TaskDeadline, onDeleteDeadline: (TaskDeadline) -> Unit) {

    val daysRemaining = calculateDaysRemaining(deadline.dueDate)

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

            IconButton(onClick = { onDeleteDeadline(deadline) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Aufgabe löschen",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}