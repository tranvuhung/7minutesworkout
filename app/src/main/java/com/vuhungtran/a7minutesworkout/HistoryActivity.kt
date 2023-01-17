package com.vuhungtran.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vuhungtran.a7minutesworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}