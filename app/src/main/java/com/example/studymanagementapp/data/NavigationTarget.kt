package com.example.studymanagementapp.data

/**
 * Definiert die Navigationsziele für die NavigationBar.
 * Wird genutzt um den aktuell ausgewählten Bildschirm zu beschreiben.
 */
enum class NavigationTarget {
    /**
     * Repräsentiert die MainActivity, welche den Pomodoro-Timer enthält.
     */
    TIMER,

    /**
     * Repräsentiert den Lernplaner, welcher den Wochenplaner und Deadline-Manager beinhaltet.
     */
    PLANNER
}