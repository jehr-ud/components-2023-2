package com.ud.candicrushud

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

        binding.btnGameCodeCreate.setOnClickListener {
            var auth = FirebaseAuth.getInstance()

            var currentUser = auth.currentUser

            var gameCode = binding.gameCode.text.toString()

            if (currentUser != null && gameCode != null){
                Log.d(TAG, "Entrando a guardar datos")
                var playerName = currentUser.displayName
                var playerUID = currentUser.uid

                var player1 = Player(playerName, playerUID)


                val game = Game(gameCode, player1, null, playerUID, false, false, null)
                gameReference?.child(gameCode)?.setValue(game)?.addOnSuccessListener{
                    // message ok
                }?.addOnFailureListener{
                    // message fail
                }

                Log.d(TAG, "Finaliza")
            }
        }

        binding.btnGameCodeUse.setOnClickListener {

        }
    }

    companion object{
        private val TAG = "CREATIONGAME"
    }
}