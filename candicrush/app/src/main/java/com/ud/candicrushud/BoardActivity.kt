package com.ud.candicrushud

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat

class BoardActivity : AppCompatActivity() {

    private lateinit var boardLayout: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        boardLayout = findViewById<TableLayout>(R.id.layoutBoard)
        generateBoard()
    }

    private fun getButtonWidth(numberRows: Int) : Int{
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        return (screenWidth / numberRows)
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

        for (row in 1..numberRows){
            var tableRow = TableRow(this)

            tableRow.setPadding(0, 0, 0, 0)

            for (col in 1..numberCols){
                var button = Button(this)
                button.id = row * (col + 10)

                val buttonWidth = getButtonWidth(numberRows)
                val buttonHeight = resources.getDimensionPixelSize(R.dimen.board_button_height)
                val params = TableRow.LayoutParams(buttonWidth, buttonHeight)

                button.setPadding(0, 0, 0, 0)
                params.setMargins(0, 0, 0, 0)
                button.layoutParams = params

                button.backgroundTintList = ColorStateList.valueOf(colors.random())

                tableRow.addView(button)
            }

            boardLayout.addView(tableRow)
        }
    }
}