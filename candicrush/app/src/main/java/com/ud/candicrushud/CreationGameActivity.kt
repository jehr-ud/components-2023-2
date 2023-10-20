package com.ud.candicrushud

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ud.candicrushud.databinding.ActivityCreationGameBinding
import com.ud.candicrushud.model.Game
import com.ud.candicrushud.model.Player


class CreationGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreationGameBinding
    private var gameReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreationGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        gameReference = FirebaseDatabase.getInstance().getReference("games")

        var gameCode = binding.gameCode.text.toString()

        var auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser

        var playerName = ""
        var playerUID = ""

        if (currentUser != null){
            playerName = currentUser.displayName.toString()
            playerUID = currentUser.uid.toString()
        } else{
            val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            playerName = sharedPreferences.getString(KEY_USER_NAME, "").toString()
            playerUID = sharedPreferences.getString(KEY_USER_ID, "").toString()
        }

        binding.btnGameCodeCreate.setOnClickListener {
            if (currentUser != null && gameCode != null){
                Log.d(TAG, "Entrando a guardar datos")

                var player1 = Player(playerName, playerUID)

                val game = Game(gameCode, player1, null, playerUID, false, false, null)
                gameReference?.child(gameCode)?.setValue(game)?.addOnSuccessListener{
                    Toast.makeText(this, R.string.code_message_creation_ok, Toast.LENGTH_LONG)
                }?.addOnFailureListener{
                    Toast.makeText(this, R.string.code_message_creation_fail, Toast.LENGTH_LONG)
                }

                Log.d(TAG, "Finaliza")
            }
        }

        binding.btnGameCodeUse.setOnClickListener {
            var game = gameReference?.child(gameCode) as Game

            game.player2 = Player(playerName, playerUID)
            game.isStart = true

            gameReference?.child(gameCode)?.setValue(game)?.addOnSuccessListener{
                goToBoard()
            }?.addOnFailureListener{
                // message fail
            }
        }
    }

    fun goToBoard(){
        val intent = Intent(this, BoardActivity::class.java)
        startActivity(intent)
    }

    companion object{
        private val TAG = "CREATIONGAME"
        private const val PREFS_NAME = "Candy-crushUD"
        private const val KEY_USER_ID = "userId"
        private const val KEY_USER_NAME = "userName"
    }
}