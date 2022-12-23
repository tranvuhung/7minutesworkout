package com.vuhungtran.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.vuhungtran.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    //Step 1 - START
    //Variable for Rest Timer and later on we will initialize it.
    private var resetTime: CountDownTimer? = null
    // Variable for timer progress. As initial value the rest progress is set to 0. As we are about to start.
    private var restProgress = 0
    //END

    private var binding: ActivityExerciseBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setOnClickListener {
            onBackPressed()
        }

        //Step: 4 - Calling the function to make it visible on screen.)-->
        //START
        setupRestView() // REST View is set in this function
        //END
    }

    //STEP 2:
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        resetTime = object : CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise.",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }.start()
    }

    //STEP 3: Setting up get ready view
    //START
    /**
     * Function is used to set the timer for REST.
     */
    private fun setupRestView(){
        if(resetTime != null){
            resetTime!!.cancel()
            restProgress = 0
        }
        // This function is used to set the progress details.
        setRestProgressBar()
    }

    override fun onDestroy() {
        if(resetTime != null){
            resetTime?.cancel()
            restProgress = 0
        }
        super.onDestroy()
        binding = null
    }
}