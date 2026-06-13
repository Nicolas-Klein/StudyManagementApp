package com.example.studymanagementapp

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.studymanagementapp.ui.components.NavigationTarget
import com.example.studymanagementapp.ui.components.SharedBottomNavigationBar
import com.example.studymanagementapp.ui.screens.PomodoroMainScreen
import com.example.studymanagementapp.ui.theme.StudyManagementAppTheme

enum class PomodoroState {
    FOCUS,
    SHORT_BREAK,
    LONG_BREAK
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyManagementAppTheme {
                Scaffold(
                    bottomBar = { SharedBottomNavigationBar(currentScreen = NavigationTarget.TIMER) }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        PomodoroMainScreen()
                    }

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun finish() {
        super.finish()

        overrideActivityTransition(
            Activity.OVERRIDE_TRANSITION_CLOSE,
            R.anim.fade_in,
            R.anim.fade_out
        )
    }
}


