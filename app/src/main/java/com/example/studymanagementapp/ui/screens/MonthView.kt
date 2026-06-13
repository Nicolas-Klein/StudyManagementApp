package com.example.studymanagementapp.ui.screens

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.ui.components.MonthviewCard
import com.example.studymanagementapp.utils.calculateDaysRemaining



@Composable
fun MonthView(innerPadding: PaddingValues, deadlineList: List<TaskDeadline>, onDeleteDeadlineClick: (TaskDeadline) -> Unit) {

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
                MonthviewCard(deadline = deadline, onDeleteDeadline = onDeleteDeadlineClick)
            }
        }

    }



}



