package com.vuhungtran.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vuhungtran.a7minutesworkout.databinding.ActivityBmiActivityBinding

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiActivityBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATOR BMI"
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}