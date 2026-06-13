package com.example.studymanagementapp.storage

import android.content.Context
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StorageManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("PlannerPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveTodoTasks(tasks: List<TaskForDay>) {
        val jsonString = gson.toJson(tasks)
        sharedPreferences.edit().putString("todo_tasks", jsonString).apply()
    }

    fun loadTodoTasks(): List<TaskForDay> {
        val jsonString = sharedPreferences.getString("todo_tasks", null) ?: return emptyList()
        val type = object : TypeToken<List<TaskForDay>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    fun saveDeadlines(tasks: List<TaskDeadline>) {
        val jsonString = gson.toJson(tasks)
        sharedPreferences.edit().putString("deadline_task", jsonString).apply()
    }

    fun loadDeadlines(): List<TaskDeadline> {
        val jsonString = sharedPreferences.getString("deadline_task", null) ?: return emptyList()
        val type = object : TypeToken<List<TaskDeadline>>() {}.type
        return gson.fromJson(jsonString, type)
    }

}