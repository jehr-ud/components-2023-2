package com.ud.candicrushud.model

import android.widget.Button
import java.text.Format

class Board (val numberRows: Int,  val numberCols: Int){

    val list = listOf<Cell>()

    var minBomb: Int = calculateBombs()


    fun addElementList(button:Button,color:Int,pos:Int){

    }

    private fun calculateBombs():Int{
        return ((numberRows * numberCols) * 0.2).toInt()
    }

}