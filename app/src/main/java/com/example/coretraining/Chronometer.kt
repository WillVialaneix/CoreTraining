package com.example.coretraining

import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.media.ToneGenerator.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_chronometer.*

class Chronometer : AppCompatActivity() {
    private lateinit var countDownTimer: CountDownTimer
    var countdownRunning = false
    private var startTimeMs: Long = Workout.lengthSeconds.toLong() * 1000
    var mTimeLeftInMillis = startTimeMs
    private var toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 200)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chronometer)

        startButton?.setOnClickListener {
            if (countdownRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
            startButton.setText(if (countdownRunning) R.string.stop else R.string.start)
        }

        resetButton?.setOnClickListener {
            resetTimer()
        }

        updateCountDownText()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflates the menu, this adds item to the action bar if not present
        menuInflater.inflate(R.menu.menu_chronometer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuBack -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startTimer() {
        toneGen1.startTone(TONE_CDMA_PIP, 150)
        countDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
                val timeLeftInSec = (mTimeLeftInMillis / 1000).toInt()
                if (timeLeftInSec % 5 == 0)
                    toneGen1.startTone(TONE_CDMA_ABBR_INTERCEPT, 150)
            }

            override fun onFinish() {
                countdownRunning = false
            }
        }.start()

        countdownRunning = true
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        countdownRunning = false
    }
    private fun resetTimer() {
        mTimeLeftInMillis = startTimeMs
        updateCountDownText()
    }
    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted: String = "%02d:%02d".format(minutes, seconds)
        textCountdown.text = timeLeftFormatted
    }
}
