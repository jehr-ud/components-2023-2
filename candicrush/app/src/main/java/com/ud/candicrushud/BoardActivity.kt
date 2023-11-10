package com.ud.candicrushud

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ud.candicrushud.listeners.ButtonGestureListener
import com.ud.candicrushud.model.Board
import com.ud.candicrushud.model.Cell
import com.ud.candicrushud.model.Game
import com.ud.candicrushud.model.Player
import com.ud.candicrushud.utils.HandleMenu

class BoardActivity : AppCompatActivity() {

    private lateinit var boardLayout: TableLayout
    private var gameReference: DatabaseReference? = null
    private var codeReference: DatabaseReference? = null
    private var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        FirebaseApp.initializeApp(this);

        boardLayout = findViewById<TableLayout>(R.id.layoutBoard)
        val gameCode = intent.getStringExtra(KEY_INTENT_CODE)

        if (gameCode != null){
            gameReference = FirebaseDatabase.getInstance().getReference("games")
            codeReference = gameReference?.child(gameCode)

            codeReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    game = dataSnapshot.getValue(Game::class.java)
                    loadBoard()

                    addListenerToGame(codeReference)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@BoardActivity, "Database error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun addListenerToGame(reference: DatabaseReference?){
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val game = dataSnapshot.getValue(Game::class.java)

                    if (game != null){
                        Toast.makeText(this@BoardActivity, "Refresh the board..", Toast.LENGTH_SHORT).show()
                        loadBoard()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@BoardActivity, "Error waiting for code..", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return HandleMenu.handleOptionsItemSelected(this, item)
    }

    private fun getButtonWidth(numberCols: Int) : Int{
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        return (screenWidth / numberCols)
    }

    private fun loadBoard(){
        val numberRows = 5
        val numberCols = 5

        if (game?.board == null){
            Toast.makeText(this@BoardActivity, "Error Loading the game", Toast.LENGTH_SHORT).show()
            return
        }


        var board = game?.board
        var counter = 0

        boardLayout.removeAllViews()

        for (row in 1..numberRows) {
            val tableRow = TableRow(this)
            tableRow.setPadding(0, 0, 0, 0)

            for (col in 1..numberCols) {

                var cell: Cell? = board?.list?.get(counter) ?: continue

                val button = Button(this)
                val buttonId = cell?.id!!

                button.id = buttonId

                val buttonWidth = getButtonWidth(numberCols)
                val buttonHeight = resources.getDimensionPixelSize(R.dimen.board_button_height)
                val params = TableRow.LayoutParams(buttonWidth, buttonHeight)

                button.setPadding(0, 0, 0, 0)
                params.setMargins(0, 0, 0, 0)
                button.layoutParams = params

                button.backgroundTintList = ColorStateList.valueOf(cell.color)

                val gestureDetector = GestureDetector(this, ButtonGestureListener(this, buttonId))

                button.setOnTouchListener { v, event ->
                    gestureDetector.onTouchEvent(event)
                }
                tableRow.addView(button)
                counter++
            }

            boardLayout.addView(tableRow)
        }
    }

    companion object{
        private val TAG = "BOARDACTIVITY"
        private const val KEY_INTENT_CODE = "game_code"
    }
}