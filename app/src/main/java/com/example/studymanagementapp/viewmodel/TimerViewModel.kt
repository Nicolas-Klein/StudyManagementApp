package com.example.studymanagementapp.viewmodel

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.studymanagementapp.data.PomodoroState
import com.example.studymanagementapp.utils.PomodoroConfig

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    var timeLeftInMillis by mutableStateOf(25 * 60 * 1000L)
        private set

    var isTimerRunning by mutableStateOf(false)
        private set

    var currentScreen by mutableStateOf(PomodoroState.FOCUS)
        private set

    var focusCycleCount by mutableStateOf(0)


    private var totalFocusTime = PomodoroConfig.FOCUS_TIME
    private var totalBreakTime = PomodoroConfig.SHORT_BREAK_TIME
    private var longBreakTime = PomodoroConfig.LONG_BREAK_TIME

    private var countDownTimer: CountDownTimer? = null

    private var mediaPlayer: MediaPlayer? = null

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

    private fun updateTimeForCurrentScreen() {
        timeLeftInMillis = when(currentScreen) {
            PomodoroState.FOCUS -> totalFocusTime
            PomodoroState.SHORT_BREAK -> totalBreakTime
            PomodoroState.LONG_BREAK -> longBreakTime
        }
    }

    fun pauseTimer() {
        stopAlarm()

        countDownTimer?.cancel()
        isTimerRunning = false
    }

    fun resetTimer() {
        pauseTimer()
        updateTimeForCurrentScreen()
    }

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

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
        stopAlarm()
    }

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