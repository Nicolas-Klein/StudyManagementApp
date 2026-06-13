package com.example.studymanagementapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay
import com.example.studymanagementapp.storage.StorageManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StorageManagerTest {

    @Test
    fun saveAndLoadTasks() {
        println("Test save and load of tasks")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val storageManager = StorageManager(appContext)

        val testList = listOf(TaskForDay(99, "Speicher Task", "Freitag"))

        storageManager.saveTodoTasks(testList)

        val loadedList = storageManager.loadTodoTasks()

        assertEquals(1, loadedList.size)
        assertEquals("Speicher Task", loadedList[0].title)
        assertEquals("Freitag", loadedList[0].dayOfTask)
    }

    @Test
    fun saveAndLoadDeadlines() {
        println("Test save and load of deadlines")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val storageManager = StorageManager(appContext)

        val testList = listOf(TaskDeadline(99, "Speicher Deadline", "22.12.2026"))

        storageManager.saveDeadlines(testList)

        val loadedList = storageManager.loadDeadlines()

        assertEquals(1, loadedList.size)
        assertEquals("Speicher Deadline", loadedList[0].title)
        assertEquals("22.12.2026", loadedList[0].dueDate)
    }

}