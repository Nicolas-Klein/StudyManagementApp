package com.example.studymanagementapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (selectedTabIndex){
                        Tabs.WOCHE -> println("Woche")
                        Tabs.MONAT -> println("Monat")
                    }
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

