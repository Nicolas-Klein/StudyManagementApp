package com.example.studymanagementapp

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.studymanagementapp.ui.screens.PlanerScreen
import org.junit.Rule
import org.junit.Test

class PlannerUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun plannerScreenUiTest() {
        composeTestRule.setContent {
            PlanerScreen()
        }

        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription("Add").performClick()

        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertExists()

        composeTestRule.onNodeWithText("Titel / Beschreibung").performTextInput("Test Aufgabe")

        composeTestRule.onNodeWithText("Speichern").performClick()

        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertDoesNotExist()
    }
}