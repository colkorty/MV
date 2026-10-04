package com.colkorty.mv.functions

import android.graphics.Rect
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.colkorty.mv.R
import java.util.Random

fun generateHearts(container: ViewGroup, onFinished: (() -> Unit)? = null) {
    val random = Random()
    val placedHearts = mutableListOf<Rect>()

    container.post {
        val width = container.width
        val height = container.height
        val baseSize = (90 * container.context.resources.displayMetrics.density).toInt()

        repeat(30) { index ->
            var rect: Rect
            var attempts = 0

            do {
                val scale = 0.5f + random.nextFloat() * 1.0f
                val currentSize = (baseSize * scale).toInt()
                val x = random.nextInt(maxOf(1, width - currentSize))
                val y = if (height - currentSize > 150) {
                    random.nextInt(150, height - currentSize)
                } else {
                    150
                }

                rect = Rect(x, y, x + currentSize, y + currentSize)
                attempts++

                val intersects = placedHearts.any { Rect.intersects(it, rect) }
            } while (intersects && attempts < 1000)

            placedHearts.add(rect)

            val heart = ImageView(container.context)
            heart.setImageResource(R.drawable.heart)

            val params = FrameLayout.LayoutParams(rect.width(), rect.height())
            params.leftMargin = rect.left
            params.topMargin = rect.top

            heart.layoutParams = params
            heart.rotation = -40f + random.nextFloat() * 90f

            container.addView(heart)

            if (index == 29) {
                onFinished?.invoke()
            }
        }
    }
}