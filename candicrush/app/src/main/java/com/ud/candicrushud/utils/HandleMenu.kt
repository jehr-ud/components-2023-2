package com.ud.candicrushud.utils

import android.content.Intent
import android.view.MenuItem
import com.ud.candicrushud.R
import com.ud.candicrushud.BoardActivity
import com.ud.candicrushud.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import android.app.Activity

class HandleMenu {

    companion object {
        fun handleOptionsItemSelected(activity: Activity, item: MenuItem): Boolean{
            return when (item.itemId) {
                R.id.main_menu_play -> {
                    val intent = Intent(activity, BoardActivity::class.java)
                    activity.startActivity(intent)
                    return true
                }
                R.id.main_menu_settings -> {
                    return true
                }
                R.id.main_menu_profile -> {
                    val intent = Intent(activity, LoginActivity::class.java)
                    activity.startActivity(intent)
                    return true
                }
                R.id.main_menu_logout -> {
                    var auth = FirebaseAuth.getInstance()
                    auth.signOut()

                    val intent = Intent(activity, LoginActivity::class.java)
                    activity.startActivity(intent)

                    return true
                }
                else -> return false
            }
        }
    }

}