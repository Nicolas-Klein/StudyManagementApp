package com.example.studymanagementapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.studymanagementapp.data.NavigationTarget
import com.example.studymanagementapp.ui.components.SharedBottomNavigationBar
import com.example.studymanagementapp.ui.components.SharedTopBar
import com.example.studymanagementapp.ui.screens.PomodoroMainScreen
import com.example.studymanagementapp.ui.theme.StudyManagementAppTheme
import com.example.studymanagementapp.viewmodel.TimerViewModel

class MainActivity : ComponentActivity() {

    private var taskIdState by mutableStateOf<Int?>(null)
    private var deadlineIdState by mutableStateOf<Int?>(null)

    private val timerViewModel by viewModels<TimerViewModel>()


    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        extractIdsFromIntent(intent)

        enableEdgeToEdge()
        setContent {
            StudyManagementAppTheme {
                Scaffold(
                    bottomBar = { SharedBottomNavigationBar(currentScreen = NavigationTarget.TIMER) },
                    topBar = { SharedTopBar("Focus-Timer") }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        PomodoroMainScreen(
                            initialTaskName = taskIdState,
                            initialDeadlineName = deadlineIdState,
                            viewModel = timerViewModel
                        )
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        extractIdsFromIntent(intent)
    }

    private fun extractIdsFromIntent(intent: Intent?){

        if(intent == null) {
            println("DEBUG_TIMER: Intent ist null")
            return
        }

        val taskId = intent.getIntExtra("EXTRA_TASK_ID", -1)
        val deadlineId = intent.getIntExtra("EXTRA_DEADLINE_ID", -1)

        println("DEBUG_TIMER: Ausgelesene Task-ID = ${taskId}, Deadline-ID = $deadlineId")

        taskIdState = if (taskId != -1) taskId else null
        deadlineIdState = if (deadlineId != -1) deadlineId else null

    }
}


