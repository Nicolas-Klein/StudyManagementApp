package com.example.studymanagementapp.data

data class TaskForDay(
    val id: Int,
    val title: String,
    val dayOfTask: String,
    val linkedDeadline: Int? = null,
)