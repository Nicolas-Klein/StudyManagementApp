package com.example.studymanagementapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


enum class Tabs {
    WOCHE,
    MONAT
}

enum class Weekdays {
    MO,
    DI,
    MI,
    DO,
    FR,
    SA,
    SO
}

@Composable
fun PlannerScreen() {
    var selectedTabIndex by remember { mutableStateOf(Tabs.WOCHE) }


    var showDialog by remember { mutableStateOf(false) }

    var taskTitleInput by remember { mutableStateOf("") }
    var deadlineDateInput by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = if (selectedTabIndex == Tabs.WOCHE) "Neue Aufgabe hinzufügen" else "Neue Deadline hinzufügen")
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = taskTitleInput,
                        onValueChange = { taskTitleInput = it },
                        label = { Text("Titel / Beschreibung" ) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (selectedTabIndex == Tabs.MONAT) {
                        OutlinedTextField(
                            value = deadlineDateInput,
                            onValueChange = { deadlineDateInput = it },
                            label = { Text("Fälligkeitsdatum") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedTabIndex == Tabs.WOCHE){
                            println("Speicher Wochenaufgabe: $taskTitleInput")
                        } else {
                            println("Speicher Deadline: $taskTitleInput am $deadlineDateInput")
                        }

                        taskTitleInput = ""
                        deadlineDateInput = ""
                        showDialog = false
                    }
                ) {
                    Text("Speichern")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (selectedTabIndex){
                        Tabs.WOCHE -> println("Woche")
                        Tabs.MONAT -> println("Monat")
                    }

                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex.ordinal) {
                Tabs.entries.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex.ordinal == index,
                        onClick = { selectedTabIndex = title },
                        text = { Text(text = title.toString()) }
                    )
                }
            }
        }

    ) { innerPadding ->

        Column(modifier = Modifier.fillMaxSize()) {


            when(selectedTabIndex) {
                Tabs.WOCHE -> WeekView(innerPadding)
                Tabs.MONAT -> MonthView(innerPadding)
            }
        }

    }



}

