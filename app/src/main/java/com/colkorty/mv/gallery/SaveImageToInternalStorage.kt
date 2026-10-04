package com.colkorty.mv.gallery

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

fun Context.saveImageToInternalStorage(uri: Uri) {
    val directory = File(getExternalFilesDir(null), "rand_images")
    if (!directory.exists()) {
        directory.mkdirs()
    }

    var name = "temp_${System.currentTimeMillis()}.jpg"
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst()) {
            name = cursor.getString(nameIndex)
        }
    }

    val file = File(directory, name)

    contentResolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
}