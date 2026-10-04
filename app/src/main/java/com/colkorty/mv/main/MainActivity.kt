package com.colkorty.mv.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.colkorty.mv.R
import com.colkorty.mv.alarm.dailyAlarm
import com.colkorty.mv.functions.generateHearts
import com.colkorty.mv.gallery.GalleryFragment

class MainActivity : AppCompatActivity() {
    private var isHeartsReady = false
    private var isPhotosReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            !isHeartsReady || !isPhotosReady
        }

        setContentView(R.layout.activity_main)

        dailyAlarm(this)

        val viewPager = findViewById<ViewPager2>(R.id.viewVerticalPager)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> GalleryFragment()
                    1 -> HorizontalPagerAdapter()
                    else -> HorizontalPagerAdapter()
                }
            }
        }

        viewPager.setCurrentItem(1, false)

        generateHearts(findViewById(R.id.heartContainer)) {
            isHeartsReady = true
        }
    }

    fun notifyPhotosReady() {
        isPhotosReady = true
    }
}