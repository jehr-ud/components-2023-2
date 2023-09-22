package com.ud.candicrushud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    lateinit var btnPlay: Button
    lateinit var editTextName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnPlay = findViewById<Button>(R.id.btnPlayHome)
        editTextName = findViewById<EditText>(R.id.editTextName)

        btnPlay.setOnClickListener{
           if (isFillFields()){
               Toast.makeText(this,"Debe llenar el nombre",Toast.LENGTH_LONG).show();
           } else{
               goToGame()
           }
        }
    }

    fun isFillFields(): Boolean{
        return editTextName.text.isEmpty()
    }

    fun goToGame(){
        val intent = Intent(this, BoardActivity::class.java)
        startActivity(intent)
    }
}