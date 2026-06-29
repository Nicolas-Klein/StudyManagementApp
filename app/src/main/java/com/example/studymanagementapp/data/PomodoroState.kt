package com.example.studymanagementapp.data

/**
 * Definiert die Phasen des Pomodoro-Timer.
 * Wird verwendet um die aktuelle Phase zu ändern.
 */
enum class PomodoroState {
    /**
     * Repräsentiert die Fokus-Phase des Pomodoro-Timer.
     */
    FOCUS,

    /**
     * Repräsentiert die kurze Pause-Phase des Pomodoro-Timer.
     */
    SHORT_BREAK,

    /**
     * Repräsentiert die lange Pause-Phase des Pomodoro-Timer.
     */
    LONG_BREAK
}