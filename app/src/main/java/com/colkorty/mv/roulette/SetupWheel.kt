package com.colkorty.mv.roulette

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun setupWheel(wheel: RecyclerView, items: List<String>, context: Context,) {
    val adapter = RouletteAdapter(items)
    wheel.adapter = adapter
    wheel.layoutManager = LinearLayoutManager(context)

    wheel.setOnTouchListener { v, _ -> 
        v.performClick()
        true 
    }

    if (items.isNotEmpty()) {
        val middle = Int.MAX_VALUE / 2
        val startPos = middle - (middle % items.size)
        wheel.scrollToPosition(startPos + (0 until items.size).random())
    }
}