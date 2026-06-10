package com.example.studymanagementapp


import android.content.Context
import android.widget.CalendarView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex.ordinal) {
            Tabs.entries.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.ordinal == index,
                    onClick = { selectedTabIndex = title },
                    text = { Text(text = title.toString()) }
                )
            }
        }

        when(selectedTabIndex) {
            Tabs.WOCHE -> WeekView()
            Tabs.MONAT -> MonthView()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekView() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var state by remember { mutableStateOf(Weekdays.MO) }
        val titles = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag")
        Column {
            SecondaryTabRow(selectedTabIndex = state.ordinal) {
                Weekdays.entries.forEachIndexed { index, title ->
                    Tab(
                        selected = state.ordinal == index,
                        onClick = { state = title },
                        text = { Text(text = title.toString(), maxLines = 2, overflow = TextOverflow.Ellipsis) },
                    )
                }
            }

            when(state) {
                Weekdays.MO -> WeekdayView(Weekdays.MO)
                Weekdays.DI -> WeekdayView(state)
                Weekdays.MI -> WeekdayView(state)
                Weekdays.DO -> WeekdayView(state)
                Weekdays.FR -> WeekdayView(state)
                Weekdays.SA -> WeekdayView(state)
                Weekdays.SO -> WeekdayView(state)
            }
        }
    }
}

@Composable
fun MonthView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Hier entsteht der Monatsplan!", style = MaterialTheme.typography.headlineMedium)


    }
}

@Composable
fun WeekdayView(weekday: Weekdays) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(weekday.toString(), style = MaterialTheme.typography.headlineMedium)
    }
}