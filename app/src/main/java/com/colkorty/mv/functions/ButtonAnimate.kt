package com.colkorty.mv.functions

import android.view.MotionEvent
import android.view.View

fun View.buttonAnimate() {
    this.setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> view.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).start()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
                if (event.action == MotionEvent.ACTION_UP) {
                    view.performClick()
                }
            }
        }
        true
    }
}