package com.example.studymanagementapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate

data class TaskForDay(
    val id: Int,
    val title: String,
    val dayOfTask: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekView(innerPadding: PaddingValues) {

    val dayOfWeek = LocalDate.now().dayOfWeek

    val weekday = when(dayOfWeek){
        DayOfWeek.MONDAY -> Weekdays.MO
        DayOfWeek.TUESDAY -> Weekdays.DI
        DayOfWeek.WEDNESDAY -> Weekdays.MI
        DayOfWeek.THURSDAY -> Weekdays.DO
        DayOfWeek.FRIDAY -> Weekdays.FR
        DayOfWeek.SATURDAY -> Weekdays.SA
        DayOfWeek.SUNDAY -> Weekdays.SO
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(innerPadding)

    ) {
        var state by remember { mutableStateOf(weekday) }
        Column {
            SecondaryTabRow(selectedTabIndex = state.ordinal) {
                Weekdays.entries.forEachIndexed { index, title ->
                    Tab(
                        selected = state.ordinal == index,
                        onClick = { state = title },
                        text = { Text(text = title.toString()) },
                    )
                }
            }

            when(state) {
                Weekdays.MO -> WeekdayView(state)
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
fun WeekdayView(weekday: Weekdays) {

    var counter by remember { mutableIntStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardColors(containerColor = Color.Gray, contentColor = Color.DarkGray, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Black)
        ) {
            Text(
                modifier = Modifier.padding(32.dp),
                text = "hello Word"
            )
        }
    }



}