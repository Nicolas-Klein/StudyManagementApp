package com.example.studymanagementapp

import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.utils.calculateDaysRemaining
import com.example.studymanagementapp.utils.filterTasksByDay
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

class HelperFunctionsTest {

    @Test
    fun calculateDaysRemainingTest() {
        println("Testing Days Remaining Calculation")

        val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val dayString = LocalDate.now().plusWeeks(1).format(dateFormat)

        val daysRemaining = calculateDaysRemaining(dayString)

        assertEquals(7, daysRemaining)
    }

    @Test
    fun filterTasksByDayTest() {
        println("Testing task per day filter")

        val dummyTasks = listOf(
            TaskForDay(1, title = "Task 1", dayOfTask = "Montag"),
            TaskForDay(2, title = "Task 2", dayOfTask = "Montag"),
            TaskForDay(3, title = "Task 3", dayOfTask = "Mittwoch"),
        )

        val tasksForMonday = filterTasksByDay(dummyTasks, "Montag")
        val tasksForWednesday = filterTasksByDay(dummyTasks, "Mittwoch")
        val tasksForFriday = filterTasksByDay(dummyTasks, "Freitag")

        assertEquals(2, tasksForMonday.size)
        assertEquals("Task 3", tasksForWednesday[0].title)
        assertEquals(0, tasksForFriday.size)
    }

}