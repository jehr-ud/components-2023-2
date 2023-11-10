package com.ud.candicrushud

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.GestureDetector
import android.widget.Button
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ud.candicrushud.databinding.ActivityCreationGameBinding
import com.ud.candicrushud.listeners.ButtonGestureListener
import com.ud.candicrushud.model.Board
import com.ud.candicrushud.model.Cell
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

        gameReference = FirebaseDatabase.getInstance().getReference("games")

        binding.btnGameCodeCreate.setOnClickListener {
            var gameCode = binding.gameCode.text.toString()
            if (currentUser != null && gameCode != null){
                Log.d(TAG, "Entrando a guardar datos")

                var player1 = Player(playerName, playerUID)

                val game = Game(gameCode, player1, null, playerUID, false, false, null)
                val codeRefence = gameReference?.child(gameCode)

                codeRefence?.setValue(game)?.addOnSuccessListener{
                    Toast.makeText(this@CreationGameActivity, R.string.code_message_creation_ok, Toast.LENGTH_LONG)

                    addListenerToRegisteredCode(codeRefence, gameCode)
                }?.addOnFailureListener{
                    Toast.makeText(this@CreationGameActivity, R.string.code_message_creation_fail, Toast.LENGTH_LONG)
                }

                Log.d(TAG, "Finaliza")
            }
        }

        binding.btnGameCodeUse.setOnClickListener {
            var gameCode = binding.gameCode.text.toString()

            val codeReference = gameReference?.child(gameCode)

            codeReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val game = dataSnapshot.getValue(Game::class.java)

                    if (game != null) {
                        Toast.makeText(this@CreationGameActivity, "Game found.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@CreationGameActivity, "Game not found", Toast.LENGTH_SHORT).show()
                        return
                    }

                    game.player2 = Player(playerName, playerUID)
                    game.isStart = true
                    game.board = generateBoard()

                    codeReference.setValue(game)
                        .addOnSuccessListener {
                            Toast.makeText(this@CreationGameActivity, "Game updated successfully", Toast.LENGTH_SHORT).show()
                            goToBoard(gameCode)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@CreationGameActivity, "Failed to update game", Toast.LENGTH_SHORT).show()
                        }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@CreationGameActivity, "Database error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun generateBoard(): Board{
        val numberRows = 5
        val numberCols = 5

        val colors = listOf(
            ContextCompat.getColor(this, R.color.game_button_blue_light),
            ContextCompat.getColor(this, R.color.game_button_yellow_zinc),
            ContextCompat.getColor(this, R.color.game_button_red),
            ContextCompat.getColor(this, R.color.game_button_green)
        )

        val listCell = mutableListOf<Cell>()

        var count = 0
        for (row in 1..numberRows) {
            val tableRow = TableRow(this)
            tableRow.setPadding(0, 0, 0, 0)

            for (col in 1..numberCols) {
                count++

                val cellId = count
                var color: Int = colors.random()
                listCell.add(Cell(cellId, color, true))
            }
        }

        return Board(numberRows, numberCols, listCell, calculateBombs(numberRows, numberCols))
    }

    private fun calculateBombs(numberRows: Int, numberCols: Int):Int{
        return ((numberRows * numberCols) * 0.2).toInt()
    }

    fun addListenerToRegisteredCode(reference: DatabaseReference, gameCode: String){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val game = dataSnapshot.getValue(Game::class.java)

                    if (game?.player2 != null){
                        Toast.makeText(this@CreationGameActivity, "Game Starting..", Toast.LENGTH_SHORT).show()
                        goToBoard(gameCode)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@CreationGameActivity, "Error waiting for code..", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun goToBoard(code: String){
        val intent = Intent(this, BoardActivity::class.java)
        intent.putExtra(KEY_INTENT_CODE, code);
        startActivity(intent)
    }

    companion object{
        private val TAG = "CREATIONGAME"
        private const val PREFS_NAME = "Candy-crushUD"
        private const val KEY_USER_ID = "userId"
        private const val KEY_INTENT_CODE = "game_code"
        private const val KEY_USER_NAME = "userName"
    }
}