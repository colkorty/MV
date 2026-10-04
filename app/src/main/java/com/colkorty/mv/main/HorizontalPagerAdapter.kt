package com.colkorty.mv.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.colkorty.mv.R
import com.colkorty.mv.date.DateFragment
import com.colkorty.mv.roulette.RouletteFragment
import com.colkorty.mv.stangeText.StrangeTextFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HorizontalPagerAdapter : Fragment(R.layout.pager_horizontal) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewHorizontalPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> DateFragment()
                    1 -> RouletteFragment()
                    2 -> StrangeTextFragment()
                    else -> DateFragment()
                }
            }
        }

        viewPager.post {
            activity?.findViewById<ViewPager2>(R.id.viewVerticalPager)?.isUserInputEnabled = (viewPager.currentItem == 0)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                activity?.findViewById<ViewPager2>(R.id.viewVerticalPager)?.isUserInputEnabled = (position == 0)
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->

        }.attach()
    }
}
