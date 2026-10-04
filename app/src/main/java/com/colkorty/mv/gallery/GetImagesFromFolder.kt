package com.colkorty.mv.gallery

import java.io.File

fun getImagesFromFolder(folderPath: String): List<File> {
    val folder = File(folderPath)
    return folder.listFiles { file ->
        file.extension.lowercase() == "jpg" ||
        file.extension.lowercase() == "png" ||
        file.extension.lowercase() == "jpeg" ||
        file.extension.lowercase() == "webp" }?.toList() ?: emptyList()
}