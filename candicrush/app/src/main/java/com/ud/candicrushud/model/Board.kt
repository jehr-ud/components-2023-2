package com.ud.candicrushud.model

import android.widget.Button
import java.text.Format

class Board (
    val numberRows: Int = 4,
    val numberCols: Int = 4,
    val list: List<Cell> = listOf<Cell>(),
    var minBomb: Int = 5
){
}