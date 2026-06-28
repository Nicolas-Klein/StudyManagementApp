package com.example.studymanagementapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.studymanagementapp.data.NavigationTarget
import com.example.studymanagementapp.ui.components.SharedBottomNavigationBar
import org.junit.Rule
import org.junit.Test


class NavigationBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigationBarTest(){
        composeTestRule.setContent {
            SharedBottomNavigationBar(NavigationTarget.TIMER)
        }

        composeTestRule.onNodeWithText("Timer").assertExists()
        composeTestRule.onNodeWithText("Timer").assertIsDisplayed()

        composeTestRule.onNodeWithText("Planer").assertExists()
        composeTestRule.onNodeWithText("Timer").assertIsDisplayed()
    }
}