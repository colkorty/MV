package com.colkorty.mv.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.colkorty.mv.R

class GalleryAdapter(private val images: List<String>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageFile = images[position]

        Glide.with(holder.imageView.context).load(imageFile).into(holder.imageView)

        holder.imageView.setOnClickListener {
            onItemClick(imageFile)
        }
    }

    override fun getItemCount() = images.size
}
