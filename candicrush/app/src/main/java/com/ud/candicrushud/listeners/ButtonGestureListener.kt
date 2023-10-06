package com.ud.candicrushud.listeners

import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import android.content.Context

class ButtonGestureListener(private val context: Context, private val buttonId: Int) : GestureDetector.SimpleOnGestureListener() {
    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        // Detect left or right fling
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                // Fling to the right
                Toast.makeText(
                    context,
                    "Fling to the right for button $buttonId",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Fling to the left
                Toast.makeText(
                    context,
                    "Fling to the left for button $buttonId",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // Detect up or down fling
            if (velocityY > 0) {
                // Fling down
                Toast.makeText(
                    context,
                    "Fling down for button $buttonId",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Fling up
                Toast.makeText(
                    context,
                    "Fling up for button $buttonId",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }
}
