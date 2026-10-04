package com.colkorty.mv.roulette

import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Fragment.spinWheels(wheels: List<RecyclerView>, button: Button) {
    val view = view as? ViewGroup
    val shield = View(context)
    view?.addView(
        shield,
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    shield.setOnTouchListener { _, _ -> true }

    button.alpha = 0.5f

    viewLifecycleOwner.lifecycleScope.launch {
        wheels.forEach { wheel ->
            val layoutManager = wheel.layoutManager as LinearLayoutManager
            val currentPos = layoutManager.findFirstVisibleItemPosition()
            val offset = (20..30).random()
            val targetPos = currentPos + offset

            val scroller = object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference(): Int = SNAP_TO_START

                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    val itemView = layoutManager.findViewByPosition(currentPos)
                    val itemHeight = itemView?.height ?: 80
                    val totalDistance = offset * itemHeight
                    return 2000f / totalDistance
                }
            }

            scroller.targetPosition = targetPos
            layoutManager.startSmoothScroll(scroller)
            delay(100)
        }

        delay(3500)

        view?.removeView(shield)
        button.alpha = 1.0f
    }
}