package com.colkorty.mv.date

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.colkorty.mv.main.MainActivity
import com.colkorty.mv.R
import com.colkorty.mv.functions.buttonAnimate
import com.colkorty.mv.gallery.saveImageToInternalStorage
import com.colkorty.mv.gallery.setRandomPhoto
import com.exjunk.lib.thanos.vanish.VanishController
import com.exjunk.lib.thanos.vanish.VanishEffect
import com.exjunk.lib.thanos.vanish.VanishGLSurfaceView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DateFragment : Fragment(R.layout.fragment_date) {
    private lateinit var imagePhoto: ImageView
    private lateinit var glSurfaceView: VanishGLSurfaceView
    private lateinit var controllerContent: VanishController
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach { uri ->
                requireContext().saveImageToInternalStorage(uri)
            }
        }
    }
    private var isAnimating = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonDate: ImageButton = view.findViewById(R.id.buttonDate)
        imagePhoto = view.findViewById(R.id.imagePhoto)
        val textDate: TextView = view.findViewById(R.id.textDate)
        val containerContent: View = view.findViewById(R.id.containerContent)
        glSurfaceView = view.findViewById(R.id.glSurfaceView)
        val verticalPager = requireActivity().findViewById<ViewPager2>(R.id.viewVerticalPager)
        val horizontalPager = requireActivity().findViewById<ViewPager2>(R.id.viewHorizontalPager)

        imagePhoto.setRandomPhoto {
            controllerContent = VanishEffect.attachToView(containerContent, glSurfaceView)
            (activity as? MainActivity)?.notifyPhotosReady()
        }

        glSurfaceView.setAnimationConfig(
            duration = 1750f,
            particleSize = 3f,
        )

        buttonDate.setOnClickListener {
            if (isAnimating) return@setOnClickListener
            isAnimating = true

            buttonDate.isEnabled = false
            buttonDate.buttonAnimate()

            verticalPager.isUserInputEnabled = false
            horizontalPager.isUserInputEnabled = false

            buttonDate.alpha = 0.5f
            buttonDate.animate().alpha(0.5f).setDuration(1000).withEndAction {
                buttonDate.alpha = 1.0f
            }

            viewLifecycleOwner.lifecycleScope.launch {
                controllerContent.vanish()
                delay(100)
                containerContent.alpha = 0f

                delay(400)

                val date = randomDate()
                textDate.text = "${date.day}.${date.month}.${date.year}"

                imagePhoto.setRandomPhoto {
                    containerContent.animate().alpha(1f).setDuration(500).withEndAction {
                        controllerContent = VanishEffect.attachToView(containerContent, glSurfaceView)

                        isAnimating = false
                        buttonDate.isEnabled = true

                        verticalPager.isUserInputEnabled = true
                        horizontalPager.isUserInputEnabled = true
                    }.start()
                }
            }
        }

        imagePhoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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