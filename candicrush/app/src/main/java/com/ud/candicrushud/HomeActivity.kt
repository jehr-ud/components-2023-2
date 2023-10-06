package com.ud.candicrushud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ud.candicrushud.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPlayHome.setOnClickListener{
           if (isFillFields()){
               Toast.makeText(this,"Debe llenar el nombre",Toast.LENGTH_LONG).show();
           } else{
               goToGame()
           }
        }
    }

    private fun isFillFields(): Boolean{
        return binding.editTextName.text.isEmpty()
    }

    private fun goToGame(){
        val intent = Intent(this, BoardActivity::class.java)
        startActivity(intent)
    }
}