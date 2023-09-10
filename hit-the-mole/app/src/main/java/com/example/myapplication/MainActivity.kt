package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMainPlayLevel1.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("speed", 2500L)
            startActivity(intent)
        }

        binding.btnMainPlayLevel2.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("speed", 1500L)
            startActivity(intent)
        }

        binding.btnMainPlayLevel3.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("speed", 300L)
            startActivity(intent)
        }
    }
}