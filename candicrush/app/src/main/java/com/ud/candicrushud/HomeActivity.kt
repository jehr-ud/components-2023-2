package com.ud.candicrushud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ud.candicrushud.databinding.ActivityHomeBinding
import com.ud.candicrushud.utils.HandleMenu

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val handleMenu = HandleMenu()
        return handleMenu.navigation(this, item)
    }

    private fun isFillFields(): Boolean{
        return binding.editTextName.text.isEmpty()
    }

    private fun goToGame(){
        val intent = Intent(this, BoardActivity::class.java)
        startActivity(intent)
    }
}