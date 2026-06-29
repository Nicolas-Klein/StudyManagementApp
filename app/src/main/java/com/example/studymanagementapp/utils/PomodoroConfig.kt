package com.example.studymanagementapp.utils

/**
 * Enthält Informationen für die Pomodoro-Timer-Phasen
 */
object PomodoroConfig {
    /**
     * Reprasentiert den Standardwert für die Timer-Zeit der Fokus-Phase
     */
    const val FOCUS_TIME = 25 * 60 * 1000L
    /**
     * Reprasentiert den Standardwert für die Timer-Zeit der Fokus-Phase wenn die Deadline weniger als 7 tage entfernt ist
     */
    const val URGENT_FOCUS_TIME = 50 * 60 * 1000L
    /**
     * Reprasentiert den Standardwert für die Timer-Zeit der kurze Pause-Phase
     */
    const val SHORT_BREAK_TIME = 5 * 60 * 1000L
    /**
     * Reprasentiert den Standardwert für die Timer-Zeit der kurze Pause-Phase wenn die Deadline weniger als 7 tage entfernt ist
     */
    const val URGENT_SHORT_BREAK_TIME = 10 * 60 * 1000L
    /**
     * Reprasentiert den Standardwert für die Timer-Zeit der lange Pause-Phase
     */
    const val LONG_BREAK_TIME = 20 * 60 * 1000L
    /**
     * Reprasentiert die Hintergrundfarbe des Fokus-Timer
     */
    const val FOCUS_TIME_COLOR = 0xFFE57373
    /**
     * Reprasentiert die Hintergrundfarbe des kurze Pause-Timer
     */
    const val SHORT_BREAK_COLOR = 0xFF81C784
    /**
     * Reprasentiert die Hintergrundfarbe des lange Pause-Timer
     */
    const val LONG_BREAK_COLOR = 0xFF64B5F6
}