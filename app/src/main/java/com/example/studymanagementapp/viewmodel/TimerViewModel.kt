package com.example.studymanagementapp.viewmodel

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.studymanagementapp.data.PomodoroState
import com.example.studymanagementapp.utils.PomodoroConfig

/**
 * Das zentrale ViewModel für die Verwaltung des Pomodoro-Timers.
 * Es steuert den gesamten Lebenszyklus des Countdowns (Start, Pause, Reset),
 * verwaltet die Zustände der Fokus- und Pausenphasen und interagiert mit dem
 * [MediaPlayer], um Audiosignale bei Ablauf des Timers abzuspielen.
 */
class TimerViewModel(application: Application) : AndroidViewModel(application) {

    var timeLeftInMillis by mutableStateOf(25 * 60 * 1000L)
        private set

    var isTimerRunning by mutableStateOf(false)
        private set

    var currentScreen by mutableStateOf(PomodoroState.FOCUS)
        private set

    var focusCycleCount by mutableIntStateOf(0)


    private var totalFocusTime = PomodoroConfig.FOCUS_TIME
    private var totalBreakTime = PomodoroConfig.SHORT_BREAK_TIME
    private var longBreakTime = PomodoroConfig.LONG_BREAK_TIME

    private var countDownTimer: CountDownTimer? = null

    private var mediaPlayer: MediaPlayer? = null

    /**
     * Setzt die Timer Intervalle für die Fokus- und kurze-Pause-Phasen, falls diese geändert wurden
     *
     * @param focusMillis Die Zeit des Timer in der Fokus-Phase
     * @param breakMillis Die Zeit des Timer in der kurzen Pause-Phase
     */
    fun setTimerDuration(focusMillis: Long, breakMillis: Long) {
        if (isTimerRunning) return


        val wasRunning = isTimerRunning

        if (wasRunning) pauseTimer()

        this.totalFocusTime = focusMillis
        this.totalBreakTime = breakMillis

        println("DEBUG_SET_TIMER_DURATION: totalFocusTime: ${this.totalFocusTime}, totalBreakTime: ${this.totalFocusTime}")

        if(!isTimerRunning) {
            updateTimeForCurrentScreen()
        }

        if(wasRunning) startTimer()
    }

    /**
     * Startet den Timer
     */
    fun startTimer() {
        if (isTimerRunning) return

        stopAlarm()

        isTimerRunning = true

        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
            }

            override fun onFinish() {
                isTimerRunning = false
                handleTimerFinished()

            }
        }.start()
    }

    /**
     * Updated die Zeit die im Timer aktuellen Übrig ist.
     */
    private fun updateTimeForCurrentScreen() {
        timeLeftInMillis = when(currentScreen) {
            PomodoroState.FOCUS -> totalFocusTime
            PomodoroState.SHORT_BREAK -> totalBreakTime
            PomodoroState.LONG_BREAK -> longBreakTime
        }
    }

    /**
     * Pausiert den Timer
     */
    fun pauseTimer() {
        stopAlarm()

        countDownTimer?.cancel()
        isTimerRunning = false
    }

    /**
     * Setzt die zeit des Timer zurück.
     */
    fun resetTimer() {
        pauseTimer()
        updateTimeForCurrentScreen()
    }

    /**
     * Beinhaltet die Logik, die ausgeführt wird, wenn ein Timer 0 erreicht.
     *
     * Hier wird zwischen den verschiedenen Timer Phasen gewechselt
     */
    fun handleTimerFinished() {
        playDefaultAlarmSound(getApplication())

        if(currentScreen == PomodoroState.FOCUS) {
            focusCycleCount++

            if (focusCycleCount % 4 == 0) {
                currentScreen = PomodoroState.LONG_BREAK
                timeLeftInMillis = longBreakTime
            } else {
                currentScreen = PomodoroState.SHORT_BREAK
                timeLeftInMillis = totalBreakTime
            }
        } else {
            currentScreen = PomodoroState.FOCUS
            timeLeftInMillis = totalFocusTime
        }
    }

    /**
     * Wird vom Android-Framework automatisch aufgerufen, wenn das ViewModel nicht mehr
     * benötigt und final zerstört wird (z. B. wenn der Screen dauerhaft verlassen wird).
     *
     * Diese Methode dient als Lebenszyklus-Bereinigung (Cleanup), um Speicherlecks zu verhindern.
     * Hier wird der [MediaPlayer] hart gestoppt und dessen Systemressourcen freigegeben,
     * damit der Alarmton nicht endlos im Hintergrund weiterläuft, wenn die App geschlossen wird.
     */
    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
        stopAlarm()
    }

    /**
     * Ist für das Abspielen des Alarm-Sound zuständig.
     *
     * Beinhaltet den [MediaPlayer], welcher für das erstellen und abspeielen des Alarms verwendet wird.
     */
    fun playDefaultAlarmSound(context: Context) {
        try {
            stopAlarm()

            val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM) ?: RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION)

            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, alarmUri)

                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )

                prepare()
                start()
            }


        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Stopt den Alarm
     *
     * Stopt den Alarm und setzt den [MediaPlayer] auf null.
     */
    fun stopAlarm() {
        try {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaPlayer == null
        }
    }

}