package com.vuhungtran.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.vuhungtran.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //  The binding is name just like the name of the layout with Binding attached
    //Step 1:We create a variable for it and assign to null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Step 2: We inflate the late file by calling inflate on the Binding name
        binding = ActivityMainBinding.inflate(layoutInflater)
        //Step 3: Then replace the setContentView parameter with binding?.root
        setContentView(binding?.root)
//        val fLStartButton: FrameLayout = findViewById(R.id.flButtonStart)
        binding?.flButtonStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding?.flBMI?.setOnClickListener{
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }
        binding?.flHistory?.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}