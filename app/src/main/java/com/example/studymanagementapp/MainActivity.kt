package com.example.studymanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.studymanagementapp.ui.theme.StudyManagementAppTheme
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController

enum class PomodoroState {
    FOCUS,
    SHORT_BREAK,
    LONG_BREAK
}

enum class AppTab {
    TIMER,
    PLANNER,
    SETTINGS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyManagementAppTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    var currentTab by remember { mutableStateOf(AppTab.TIMER) }

    Scaffold (
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentTab == AppTab.TIMER,
                    onClick = { currentTab = AppTab.TIMER},
                    label = { Text("Timer") },
                    icon = { Icon(Icons.Filled.Refresh, contentDescription = "Timer") }
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.PLANNER,
                    onClick = { currentTab = AppTab.PLANNER},
                    label = { Text("Timer") },
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "Planner") }
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.SETTINGS,
                    onClick = { currentTab = AppTab.SETTINGS},
                    label = { Text("Timer") },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") }
                )
            }
        }
    ) { innerPadding ->

        Surface (
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentTab) {
                AppTab.TIMER -> {
                    PomodoroMainScreen()
                }
                AppTab.PLANNER -> {
                    PlannerScreen()
                }
                AppTab.SETTINGS -> {
                    PlaceholderSettings()
                }
            }
        }

    }
}


