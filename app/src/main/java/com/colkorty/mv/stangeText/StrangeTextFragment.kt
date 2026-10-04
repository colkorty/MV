package com.colkorty.mv.stangeText

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.colkorty.mv.R
import com.colkorty.mv.functions.buttonAnimate
import com.exjunk.lib.thanos.vanish.VanishController
import com.exjunk.lib.thanos.vanish.VanishEffect
import com.exjunk.lib.thanos.vanish.VanishGLSurfaceView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class StrangeTextFragment : Fragment(R.layout.fragment_strange_text) {
    private lateinit var glSurfaceView: VanishGLSurfaceView
    private var controllerContent: VanishController? = null
    private var isAnimating = false
    private var userInputEmpty = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewTransformed: View = view.findViewById(R.id.viewTransformed)
        val buttonTransform: Button = view.findViewById(R.id.buttonTransform)
        val editText: EditText = view.findViewById(R.id.textForTransform)
        val textTransformed: TextView = view.findViewById(R.id.textTransformed)
        glSurfaceView = view.findViewById(R.id.glSurfaceView)
        val horizontalPager = requireActivity().findViewById<ViewPager2>(R.id.viewHorizontalPager)

        textTransformed.movementMethod = ScrollingMovementMethod()

        glSurfaceView.setAnimationConfig(
            duration = 1750f,
            particleSize = 3f,
        )

        textTransformed.setOnClickListener {
            val currentText = textTransformed.text.toString()

            if (currentText.isNotEmpty() && currentText != "ЗдЕсЬ бУдЕт ВаШ тЕкСт") {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("transformed_text", currentText))
                Toast.makeText(requireContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_SHORT).show()
            }
        }

        viewTransformed.setOnClickListener {
            textTransformed.performClick()
        }

        textTransformed.setOnTouchListener { v, event ->
            val canScrollVertically = v.canScrollVertically(1) || v.canScrollVertically(-1)

            if (canScrollVertically) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        horizontalPager.isUserInputEnabled = false
                    }
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        horizontalPager.isUserInputEnabled = true
                    }
                }
            }

            false
        }

        buttonTransform.setOnClickListener {
            if (isAnimating) return@setOnClickListener
            isAnimating = true

            buttonTransform.alpha = 0.5f

            buttonTransform.isEnabled = false
            buttonTransform.buttonAnimate()
            horizontalPager.isUserInputEnabled = false

            buttonTransform.animate().alpha(0.5f).setDuration(1000).withEndAction {
                buttonTransform.alpha = 1.0f
            }

            userInputEmpty = editText.text.trim().isEmpty()

            val text = editText.text.toString()
            val newText = CharArray(text.length)
            var uppercased = true

            for (i in newText.indices) {
                if (text[i].isLetter()) {
                    if (uppercased) {
                        newText[i] = text[i].uppercaseChar()
                        uppercased = false
                    } else {
                        newText[i] = text[i].lowercaseChar()
                        uppercased = true
                    }
                } else {
                    newText[i] = text[i]
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                controllerContent = VanishEffect.attachToView(textTransformed, glSurfaceView)
                delay(14.milliseconds)
                controllerContent?.vanish()
                delay(20.milliseconds)
                textTransformed.alpha = 0f

                delay(500.milliseconds)

                if (String(newText).trim().isEmpty()) {
                    textTransformed.text = "ЗдЕсЬ бУдЕт ВаШ тЕкСт"
                }
                else {
                    textTransformed.text = String(newText).trim()
                }

                textTransformed.animate().alpha(1f).setDuration(500).withEndAction {
                        controllerContent = VanishEffect.attachToView(textTransformed, glSurfaceView)
                        isAnimating = false
                        buttonTransform.isEnabled = true
                        horizontalPager.isUserInputEnabled = true
                }.start()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (::glSurfaceView.isInitialized) glSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (::glSurfaceView.isInitialized) glSurfaceView.onResume()
    }
}