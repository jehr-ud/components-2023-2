package com.ud.candicrushud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow

class BoardActivity : AppCompatActivity() {

    lateinit var boardLayout: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        boardLayout = findViewById<TableLayout>(R.id.layoutBoard)
        generateBoard()
    }

    fun generateBoard(){
        for (row in 1..9){
            var tableRow = TableRow(this)
            for (col in 1..9){
                var button = Button(this)
                button.text = "$col"

                tableRow.addView(button)
            }

            boardLayout.addView(tableRow)
        }
    }
}