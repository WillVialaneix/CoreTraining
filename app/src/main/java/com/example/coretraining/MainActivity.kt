package com.example.coretraining

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_chronometer.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWorkoutTimeText()

        // tip: the "?." means it won't fail if mainStart is null
        mainStart?.setOnClickListener {
            // The next two lines are key: open new activity, passing the intent
            val intent = Intent(this, Chronometer::class.java)
            startActivity(intent)
        }

        plusButton?.setOnClickListener {
            Workout.lengthSeconds = Workout.lengthSeconds + 1
            setWorkoutTimeText()
        }

        minusButton?.setOnClickListener {
            Workout.lengthSeconds = Workout.lengthSeconds - 1
            setWorkoutTimeText()
        }
    }

    private fun setWorkoutTimeText() {
        val workoutTimeText: String = "%02d:%02d".format(0, Workout.lengthSeconds)
        workoutTime.text = workoutTimeText
    }


}
