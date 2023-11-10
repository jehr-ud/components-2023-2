package com.ud.candicrushud.model

data class Game (
    var code: String = "",
    var player1: Player? = null,
    var player2: Player? = null,
    var turn: String = "",
    var isStart: Boolean = false,
    var isFinished: Boolean = false,
    var winner: String? = null
)
