package com.example.studymanagementapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.storage.StorageManager
import com.example.studymanagementapp.utils.formatMillisToDateString
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.data.TaskDeadline


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerScreen() {

    // ------------------------------ Storage-Manager --------------------------------------

    val context = LocalContext.current

    val storageManager = remember { StorageManager(context) }

    var taskList by remember { mutableStateOf(listOf<TaskForDay>()) }
    var deadlineList by remember { mutableStateOf(listOf<TaskDeadline>()) }

    LaunchedEffect(Unit) { 
        taskList = storageManager.loadTodoTasks()
        deadlineList = storageManager.loadDeadlines()
    }

    // ------------------------------ UI-Elements --------------------------------------

    var selectedTabIndex by remember { mutableStateOf(Tabs.WOCHE) }


    var showDialog by remember { mutableStateOf(false) }

    var taskTitleInput by remember { mutableStateOf("") }
    var deadlineDateInput by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val dropdownElements = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag")
    var selectedDayInput by remember { mutableStateOf(dropdownElements[0]) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    if (showDialog) {

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            deadlineDateInput =
                                formatMillisToDateString(datePickerState.selectedDateMillis)
                            showDatePicker = false
                        }
                    ) {
                        Text("Ok")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false}) {
                        Text("Abbrechen")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

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

                    if(selectedTabIndex == Tabs.WOCHE) {
                        ExposedDropdownMenuBox(
                            expanded = isDropdownExpanded,
                            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded}
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                                value = selectedDayInput,
                                onValueChange = {},
                                readOnly = true,
                                label = {Text("Wochentag")},
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                            )

                            ExposedDropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false }
                            ) {
                                dropdownElements.forEach { day ->
                                    DropdownMenuItem(
                                        text = { Text(day) },
                                        onClick = {
                                            selectedDayInput = day
                                            isDropdownExpanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }

                    if (selectedTabIndex == Tabs.MONAT) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDatePicker = true }
                        ) {
                            OutlinedTextField(
                                value = deadlineDateInput,
                                onValueChange = {  },
                                label = { Text("Fälligkeitsdatum") },
                                readOnly = true,
                                enabled = false,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                            )
                        }



                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedTabIndex == Tabs.WOCHE){
                            println("Speicher Wochenaufgabe: $taskTitleInput")
                            
                            val newTask = TaskForDay(
                                id = taskList.size + 1,
                                title = taskTitleInput,
                                dayOfTask = selectedDayInput
                            )

                            taskList = taskList + newTask

                            storageManager.saveTodoTasks(taskList)
                            
                        } else {
                            println("Speicher Deadline: $taskTitleInput am $deadlineDateInput")

                            val newDeadline = TaskDeadline(
                                id = deadlineList.size + 1,
                                title = taskTitleInput,
                                dueDate = deadlineDateInput
                            )
                            deadlineList = deadlineList + newDeadline
                            storageManager.saveDeadlines(deadlineList)
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
                Tabs.WOCHE -> WeekView(innerPadding, taskList, onDeleteTaskClick = { taskToDelete ->
                    taskList = taskList.filter { it.id != taskToDelete.id }

                    storageManager.saveTodoTasks(taskList)
                })
                Tabs.MONAT -> MonthView(
                    innerPadding,
                    deadlineList,
                    onDeleteDeadlineClick = { deadlineToDelete ->
                        deadlineList = deadlineList.filter { it.id != deadlineToDelete.id }

                        storageManager.saveDeadlines(deadlineList)
                    })
            }
        }
    }
}