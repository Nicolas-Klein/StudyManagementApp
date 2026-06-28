package com.example.studymanagementapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
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
    fun plannerScreenUiDeadlineTest() {
        composeTestRule.setContent {
            PlanerScreen()
        }

        // test des Deadline Tabs
        composeTestRule.onNodeWithTag("tab_DEADLINE").performClick()
        composeTestRule.onNodeWithText("Anstehende Fristen und Klausuren").assertIsDisplayed()

        // test der Deadline erstellung
        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithText("Neue Deadline hinzufügen").assertExists()
        composeTestRule.onNodeWithText("Titel / Beschreibung").performTextInput("Test Aufgabe")
        composeTestRule.onNodeWithText("Fälligkeitsdatum").performClick()
        composeTestRule.onNodeWithText("Select date").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ok").performClick()
        composeTestRule.onNodeWithText("Speichern").performClick()
        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertDoesNotExist()


    }

    @Test
    fun plannerScreenUiWocheTest() {
        composeTestRule.setContent {
            PlanerScreen()
        }

        // test des Wochen Tabs und der dazugehörigen Wochenplaner Tabs
        composeTestRule.onNodeWithTag("tab_WOCHE").performClick()
        composeTestRule.onNodeWithText("MO").assertIsDisplayed()
        composeTestRule.onNodeWithText("MO").performClick()
        composeTestRule.onNodeWithText("Montag").assertIsDisplayed()
        composeTestRule.onNodeWithText("DI").assertIsDisplayed()
        composeTestRule.onNodeWithText("DI").performClick()
        composeTestRule.onNodeWithText("Dienstag").assertIsDisplayed()
        composeTestRule.onNodeWithText("MI").assertIsDisplayed()
        composeTestRule.onNodeWithText("MI").performClick()
        composeTestRule.onNodeWithText("Mittwoch").assertIsDisplayed()
        composeTestRule.onNodeWithText("DO").assertIsDisplayed()
        composeTestRule.onNodeWithText("DO").performClick()
        composeTestRule.onNodeWithText("Donnerstag").assertIsDisplayed()
        composeTestRule.onNodeWithText("FR").assertIsDisplayed()
        composeTestRule.onNodeWithText("FR").performClick()
        composeTestRule.onNodeWithText("Freitag").assertIsDisplayed()
        composeTestRule.onNodeWithText("SA").assertIsDisplayed()
        composeTestRule.onNodeWithText("SA").performClick()
        composeTestRule.onNodeWithText("Samstag").assertIsDisplayed()
        composeTestRule.onNodeWithText("SO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SO").performClick()
        composeTestRule.onNodeWithText("Sonntag").assertIsDisplayed()

        // test der Aufgaben erstellung
        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertExists()
        composeTestRule.onNodeWithText("Titel / Beschreibung").performTextInput("Test Aufgabe")
        composeTestRule.onNodeWithText("Wochentag").performClick()
        composeTestRule.onNodeWithText("Freitag").assertIsDisplayed()
        composeTestRule.onNodeWithText("Freitag").performClick()
        composeTestRule.onNodeWithText("Deadline").performClick()
        composeTestRule.onNodeWithText("Keine Deadline").assertIsDisplayed()
        composeTestRule.onNodeWithText("Keine Deadline").performClick()
        composeTestRule.onNodeWithText("Speichern").performClick()
        composeTestRule.onNodeWithText("Neue Aufgabe hinzufügen").assertDoesNotExist()

    }
}