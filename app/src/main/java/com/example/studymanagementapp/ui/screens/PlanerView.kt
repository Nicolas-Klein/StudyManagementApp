package com.example.studymanagementapp.ui.screens

import android.content.Intent
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
import androidx.compose.material3.DatePickerState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.studymanagementapp.MainActivity
import com.example.studymanagementapp.storage.StorageManager
import com.example.studymanagementapp.utils.formatMillisToDateString
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.data.TaskDeadline
import kotlin.collections.plus
import androidx.compose.runtime.mutableStateOf


enum class Tabs {
    WOCHE,
    DEADLINE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanerScreen() {

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
    var selectedDeadlineInput: Int? by remember { mutableStateOf(0) }

    if (showDialog) {

        if(showDatePicker) {
            ShowDatePicker(
                state = datePickerState,
                onDateSelected = {deadlineDateInput = it},
                onDismiss = {showDatePicker = false }
            )
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


                    when(selectedTabIndex){
                        Tabs.WOCHE -> {
                            WeekDayDropdown(
                            selectedDay = selectedDayInput,
                            onDaySelected = { selectedDayInput = it },
                            dropdownElements = dropdownElements
                            )
                            DeadlineDropDown(
                                dropdownElements = deadlineList,
                                onDeadlineSelected = { selectedDeadlineInput = it},
                            )

                            println("Deadline Id: $selectedDeadlineInput")
                        }
                        Tabs.DEADLINE -> DeadlineDatePicker(
                            currentDateText = deadlineDateInput,
                            onClick = { showDatePicker = true }
                        )
                    }

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedTabIndex == Tabs.WOCHE){
                            val highestId = taskList.maxOfOrNull { it.id } ?: 0

                            val newTask = TaskForDay(
                                id = highestId + 1,
                                title = taskTitleInput,
                                dayOfTask = selectedDayInput,
                                linkedDeadline = selectedDeadlineInput,
                            )

                            taskList = taskList + newTask

                            storageManager.saveTodoTasks(taskList)

                        } else {
                            val highestId = deadlineList.maxOfOrNull { it.id } ?: 0

                            val newDeadline = TaskDeadline(
                                id = highestId + 1,
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
                Tabs.WOCHE -> WeekView(innerPadding, taskList, deadlineList , onDeleteTaskClick = { taskToDelete ->
                        taskList = taskList.filter { it.id != taskToDelete.id }

                        storageManager.saveTodoTasks(taskList)
                    },
                    onSelectTaskClick = { task ->

                        val intent = Intent(context, MainActivity::class.java).apply {
                            putExtra("EXTRA_TASK_ID",task.id)
                            putExtra("EXTRA_DEADLINE_ID", task.linkedDeadline)

                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        }

                        context.startActivity(intent)
                    })
                Tabs.DEADLINE -> DeadlineView(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlineDropDown(onDeadlineSelected: (Int?) -> Unit, dropdownElements: List<TaskDeadline>) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    var selectedDeadlineTitle by remember { mutableStateOf<String?>(null) }

    ExposedDropdownMenuBox(
        expanded = isDropdownExpanded,
        onExpandedChange = { isDropdownExpanded = !isDropdownExpanded}
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            value = selectedDeadlineTitle ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    if (selectedDeadlineTitle == null) "Deadline" else "Verknüpfte Deadline"
                )
            },
            placeholder = {
                Text("Wähle...")
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Keine Deadline") },
                onClick = {
                    selectedDeadlineTitle = null
                    onDeadlineSelected(null)
                    isDropdownExpanded = false
                }
            )

            dropdownElements.forEach { deadline ->
                DropdownMenuItem(
                    text = { Text(deadline.title) },
                    onClick = {
                        onDeadlineSelected(deadline.id)
                        isDropdownExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePicker(state: DatePickerState, onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(formatMillisToDateString(state.selectedDateMillis))
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        }
    ) {
        DatePicker(state = state)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekDayDropdown(selectedDay: String, onDaySelected: (String) -> Unit, dropdownElements: List<String>){

    var isDropdownExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isDropdownExpanded,
        onExpandedChange = { isDropdownExpanded = !isDropdownExpanded}
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            value = selectedDay,
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
                        onDaySelected(day)
                        isDropdownExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun DeadlineDatePicker(currentDateText: String, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        OutlinedTextField(
            value = currentDateText,
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