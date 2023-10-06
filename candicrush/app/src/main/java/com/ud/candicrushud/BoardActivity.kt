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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ud.candicrushud.listeners.ButtonGestureListener
import com.ud.candicrushud.utils.HandleMenu

class BoardActivity : AppCompatActivity() {

    private lateinit var boardLayout: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        boardLayout = findViewById<TableLayout>(R.id.layoutBoard)
        generateBoard()
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

    private fun getButtonWidth(numberCols: Int) : Int{
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        return (screenWidth / numberCols)
    }

    private fun generateBoard(){
        val numberRows = 5
        val numberCols = 5

        val colors = listOf(
            ContextCompat.getColor(this, R.color.game_button_blue_light),
            ContextCompat.getColor(this, R.color.game_button_yellow_zinc),
            ContextCompat.getColor(this, R.color.game_button_red),
            ContextCompat.getColor(this, R.color.game_button_green)
        )

        for (row in 1..numberRows) {
            val tableRow = TableRow(this)
            tableRow.setPadding(0, 0, 0, 0)

            for (col in 1..numberCols) {
                val button = Button(this)
                val buttonId = row * (col + 10)
                button.id = buttonId

                val buttonWidth = getButtonWidth(numberCols)
                val buttonHeight = resources.getDimensionPixelSize(R.dimen.board_button_height)
                val params = TableRow.LayoutParams(buttonWidth, buttonHeight)

                button.setPadding(0, 0, 0, 0)
                params.setMargins(0, 0, 0, 0)
                button.layoutParams = params

                button.backgroundTintList = ColorStateList.valueOf(colors.random())

                val gestureDetector = GestureDetector(this, ButtonGestureListener(this, buttonId))

                button.setOnTouchListener { v, event ->
                    gestureDetector.onTouchEvent(event)
                }

                tableRow.addView(button)
            }

            boardLayout.addView(tableRow)
        }

    }
}