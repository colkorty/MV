package com.colkorty.mv.gallery

import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import java.io.File

fun deleteDialog(path: String, context: Context, view: RecyclerView) {
    AlertDialog.Builder(context)
        .setTitle("Удаление")
        .setMessage("Удалить это изображение?")
        .setPositiveButton("Удалить") { _, _ ->
            if (File(path).delete()) {
                refreshGallery(view, context)
            }
        }
        .setNegativeButton("Отмена", null)
        .show()
}