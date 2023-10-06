package com.ud.candicrushud.utils

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ud.candicrushud.R
import com.ud.candicrushud.BoardActivity
import com.ud.candicrushud.HomeActivity

open class HandleMenu: AppCompatActivity(){
    fun navigation(context: Context, item: MenuItem): Boolean{
        return when (item.itemId) {
            R.id.main_menu_play -> {
                val intent = Intent(context, BoardActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.main_menu_settings -> {
                true
            }
            R.id.main_menu_profile -> {
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }
}