package com.colkorty.mv.gallery

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import java.io.File

fun refreshGallery(gallery: RecyclerView, context: Context) {
    val imagePaths = getImagesFromFolder(
        File(
            context.getExternalFilesDir(null),
            "rand_images"
        ).absolutePath
    ).map { it.absolutePath }

    gallery.adapter = GalleryAdapter(imagePaths) { clickedImagePath ->
        deleteDialog(clickedImagePath, context, gallery)
    }
}