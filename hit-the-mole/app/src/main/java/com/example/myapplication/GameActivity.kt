package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityGameBinding
import java.util.*

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var buttons: MutableList<Button> = mutableListOf()
    private var posMole = 0
    private var points = 0
    private lateinit var txtGamePoints: TextView
    private var speed: Long = 0
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        txtGamePoints = this.binding.txtGamePoints
        mediaPlayer = MediaPlayer.create(this, R.raw.reward)
        speed = intent.getLongExtra("speed", 1000L)

        createButtonsOfGame()

        Thread {
            runGame()
        }.start()
    }

    private fun createButtonsOfGame(){
        for (i in 0..10){
            val button = Button(this)
            button.text =  "$i"

            button.setOnClickListener{
                val buttonId = it.id

                if (buttonId == posMole){
                    points++
                    if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                        mediaPlayer!!.start()
                    }
                } else{
                    points--
                }

                updatePointsInView()
            }

            button.id = i

            this.binding.layoutGame.addView(button)
            this.buttons.add(button)
        }
    }

    private fun updatePointsInView(){
        txtGamePoints.text =  "$points"
    }

    private fun runGame(){
       for (l in 0..100){
            this.posMole = rand(0, this.buttons.size)
            Log.d("POSM", this.posMole.toString())

            this.buttons[this.posMole].text = "CLICK ME (MOLE)"

            for ((i, button) in this.buttons.withIndex()){
                if (i != this.posMole){
                    button.text = "$i"
                }
            }

            Thread.sleep(this.speed)
        }
    }

    private fun rand(from: Int, to: Int) : Int {
        val random = Random()
        return random.nextInt(to - from) + from
    }
}