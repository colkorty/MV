package com.colkorty.mv.roulette

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.colkorty.mv.R

class RouletteAdapter (private val items: List<String>) : RecyclerView.Adapter<RouletteAdapter.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textRoulette)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_roulette, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items.random()
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}