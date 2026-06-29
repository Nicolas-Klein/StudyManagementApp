package com.example.studymanagementapp.data

/**
 * Repräsentiert die Deadline die User erstellen können.
 * Diese Datenklasse wird für die persistente Speicherung verwendet.
 *
 * @property id Eindeutige Kennung der Deadline (Primärschlüssel für die Zuordnung).
 * @property title Die Bezeichnung der Deadline (z.B. "Psychologie Fallstudie")
 * @property dueDate Das Fälligkeitsdatum der Deadline (Format: dd.MM.yyyy)
 */
data class TaskDeadline(
    val id: Int,
    val title: String,
    val dueDate: String
)