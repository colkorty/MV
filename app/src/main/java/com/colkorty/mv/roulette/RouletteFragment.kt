package com.colkorty.mv.roulette

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.colkorty.mv.R
import com.colkorty.mv.main.ROULETTE_ITEMS_1
import com.colkorty.mv.main.ROULETTE_ITEMS_2
import com.colkorty.mv.main.ROULETTE_ITEMS_3
import com.colkorty.mv.functions.buttonAnimate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RouletteFragment : Fragment(R.layout.fragment_roulette) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wheel1: RecyclerView = view.findViewById(R.id.wheel1)
        val wheel2: RecyclerView = view.findViewById(R.id.wheel2)
        val wheel3: RecyclerView = view.findViewById(R.id.wheel3)
        val button: Button = view.findViewById(R.id.buttonSpin)
        val verticalPager = requireActivity().findViewById<ViewPager2>(R.id.viewVerticalPager)
        val horizontalPager = requireActivity().findViewById<ViewPager2>(R.id.viewHorizontalPager)

        setupWheel(wheel1, ROULETTE_ITEMS_1, requireContext())
        setupWheel(wheel2, ROULETTE_ITEMS_2, requireContext())
        setupWheel(wheel3, ROULETTE_ITEMS_3, requireContext())

        button.buttonAnimate()

        button.setOnClickListener {
            spinWheels(listOf(wheel1, wheel2, wheel3), button)
            viewLifecycleOwner.lifecycleScope.launch {
                verticalPager.isUserInputEnabled = false
                horizontalPager.isUserInputEnabled = false
                delay(3500)
                verticalPager.isUserInputEnabled = true
                horizontalPager.isUserInputEnabled = true
            }
        }
    }
}
