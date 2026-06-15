package com.example.studymanagementapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.studymanagementapp.data.NavigationTarget
import com.example.studymanagementapp.ui.components.SharedBottomNavigationBar
import com.example.studymanagementapp.ui.components.SharedTopBar
import com.example.studymanagementapp.ui.screens.PomodoroMainScreen
import com.example.studymanagementapp.ui.theme.StudyManagementAppTheme



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyManagementAppTheme {
                Scaffold(
                    bottomBar = { SharedBottomNavigationBar(currentScreen = NavigationTarget.TIMER) },
                    topBar = { SharedTopBar("Focus-Timer") }
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
            OVERRIDE_TRANSITION_CLOSE,
            R.anim.fade_in,
            R.anim.fade_out
        )
    }
}


