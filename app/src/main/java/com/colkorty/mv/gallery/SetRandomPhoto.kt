package com.colkorty.mv.gallery

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.colkorty.mv.R
import java.io.File

fun ImageView.setRandomPhoto(onReady: (() -> Unit)? = null) {
    val directory = File(context.getExternalFilesDir(null), "rand_images")
    val currentFilePath = this.tag as? String
    val count = directory.listFiles()?.count { file ->
        file.isFile && file.extension.lowercase() in listOf("jpg", "png", "jpeg", "webp")
    } ?: 0
    val files = directory.listFiles()?.filter { file ->
        if (count > 1) file.isFile && (file.extension.lowercase() in listOf("jpg", "png", "jpeg", "webp")) && file.path != currentFilePath
        else file.isFile && (file.extension.lowercase() in listOf("jpg", "png", "jpeg", "webp"))
    }

    val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(40))

    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable?>,
            isFirstResource: Boolean
        ): Boolean {
            onReady?.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable?>?,
            dataSource: DataSource,
            isFirstResource: Boolean
        ): Boolean {
            onReady?.invoke()
            return false
        }
    }


    if (!files.isNullOrEmpty()) {
        val randomFile = files.random()

        Glide.with(this)
            .load(randomFile)
            .apply(requestOptions)
            .listener(listener)
            .into(this)

        this.tag = randomFile.path
    } else {
        Glide.with(this)
            .load(R.drawable.add_image)
            .listener(listener)
            .into(this)
    }
}
