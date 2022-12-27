package com.vuhungtran.a7minutesworkout

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.vuhungtran.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //Step 1 - START
    //Variable for Rest Timer and later on we will initialize it.
    private var resetTime: CountDownTimer? = null
    // Variable for timer progress. As initial value the rest progress is set to 0. As we are about to start.
    private var restProgress = 0
    //END
    private var exerciseTimer: CountDownTimer? = null // Variable for Exercise Timer and later on we will initialize it.
    private var exerciseProgress = 0 // Variable for the exercise timer progress. As initial value the exercise progress is set to 0. As we are about to start.

    private var exerciseTimerDuration:Long = 30
    private var exerciseList: ArrayList<ExerciseModel>? = null // We will initialize the list later.
    private var currentExercisePosition = -1 // Current Position of Exercise.

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

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

        tts = TextToSpeech(this, this)

        // TODO(Step 7 - Initializing and Assigning a default exercise list to our list variable.)
        // START
        exerciseList = Constants.defaultExerciseList()
        // END

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
                currentExercisePosition++
                setupExerciseView()
            }

        }.start()
    }

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exerciseTimerDuration.toInt() - exerciseProgress
                binding?.tvTimerExercise?.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                // TODO(Step 10 - Updating the view after completing the 30 seconds exercise.)
                // START
                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {

                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations! You have completed the 7 minutes workout.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // END
            }
        }.start()
    }

    //STEP 3: Setting up get ready view
    //START
    /**
     * Function is used to set the timer for REST.
     */
    private fun setupRestView(){
        try {
            val soundURI = Uri.parse("android.resource://com.vuhungtran.a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.upcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE

        if(resetTime != null){
            resetTime!!.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()
        // This function is used to set the progress details.
        setRestProgressBar()
    }

    private fun setupExerciseView() {

        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE

        /**
         * Here firstly we will check if the timer is running and it is not null then cancel the running timer and start the new one.
         * And set the progress to the initial value which is 0.
         */
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        // MARK: - Setting up the current exercise name and image to view to the UI element.)
        // START
        /**
         * Here current exercise name and image is set to exercise view.
         */
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        // END

        setExerciseProgressBar()
    }

    override fun onDestroy() {
        if(resetTime != null){
            resetTime?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        // MARK: - Shutting down the Text to Speech feature when activity is destroyed
        // START
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
        }

        super.onDestroy()
        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        }else{
            Log.e("TTS", "Initialization Failed!")
        }
    }

    // MARK:  Making a function to speak the text.)
    // START
    /**
     * Function is used to speak the text that we pass to it.
     */
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
    // END
}