package com.example.studymanagementapp

import android.app.Application
import android.content.Context
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.ui.screens.PomodoroMainScreen
import com.example.studymanagementapp.viewmodel.TimerViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PomodoroMainScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testStartButtonChangesToPause() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val application = context as Application

        val viewModel = TimerViewModel(application)

        val initTask = TaskForDay(
            id = 1,
            title = "heutige Aufgabe",
            dayOfTask = "Montag",
            linkedDeadline = 1
        )

        composeTestRule.setContent {
            PomodoroMainScreen(
                initialTaskName = initTask.id,
                initialDeadlineName = initTask.linkedDeadline,
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Start").assertExists()
        composeTestRule.onNodeWithText("Start").performClick()
        composeTestRule.onNodeWithText("Pause").assertExists()

        composeTestRule.onNodeWithText("Pause").assertExists()

    }

}