package com.example.studymanagementapp

import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.utils.PomodoroConfig.FOCUS_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.SHORT_BREAK_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.URGENT_FOCUS_TIME
import com.example.studymanagementapp.utils.PomodoroConfig.URGENT_SHORT_BREAK_TIME
import com.example.studymanagementapp.utils.calculateDaysRemaining
import com.example.studymanagementapp.utils.calculateTimerInterval
import com.example.studymanagementapp.utils.filterTasksByDay
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.junit.Assert.assertEquals

class HelperFunctionsTest {

    @Test
    fun calculateDaysRemainingTest() {
        println("Testing Days Remaining Calculation")

        val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val dayString = LocalDate.now().plusWeeks(1).format(dateFormat)

        val daysRemaining = calculateDaysRemaining(dayString, LocalDate.now().toString())

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

    @Test
    fun calculateTimerIntervalTest() {
        println("Calculate Timer Interval")

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val dummyTaskDeadlineNotUrgent = TaskDeadline(1, "test not urgent", LocalDate.now().plusWeeks(2).format(formatter).toString())
        val dummyTaskDeadlineUrgent = TaskDeadline(2, "test urgent", LocalDate.now().plusDays(2).format(formatter).toString())

        val notUrgentInterval = calculateTimerInterval(dummyTaskDeadlineNotUrgent)
        val urgentInterval = calculateTimerInterval(dummyTaskDeadlineUrgent)

        assertEquals(Pair(FOCUS_TIME, SHORT_BREAK_TIME), notUrgentInterval)
        assertEquals(Pair(URGENT_FOCUS_TIME, URGENT_SHORT_BREAK_TIME), urgentInterval)
    }

}