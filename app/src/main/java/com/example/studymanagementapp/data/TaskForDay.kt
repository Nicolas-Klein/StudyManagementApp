package com.example.studymanagementapp.data

/**
 * Repräsentiert die Aufgaben die ein User erstellen kann.
 * Diese Datenklasse wird für die persistente Speicherung verwendet.
 *
 * @property id Eindeutige Kennung der Aufgabe (Primärschlüssel für die Zuordnung).
 * @property title Die Bezeichnung der Aufgabe (z.B. "Kapitel 1 ersten Entwurf schreiben")
 * @property dayOfTask Der Tag dem die Aufgabe im Wochenplaner zugewiesen wurde (z.B. Montag)
 * @property linkedDeadline Die Deadline die einer Aufgabe zugewiesen wurde. Ist null, wenn der Aufgabe keine Deadline zugewiesen wurde.
 */
data class TaskForDay(
    val id: Int,
    val title: String,
    val dayOfTask: String,
    val linkedDeadline: Int? = null,
)