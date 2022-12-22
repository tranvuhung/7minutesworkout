package com.vuhungtran.a7minutesworkout

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
            Toast.makeText(
                this,
                "Here we will start the exercise.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Todo 4: TO avoid memory leak we unassign the binding once the activity is destroyed
        binding = null
    }
}