package com.tech.mymedicalshopuser.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

const val BASE_URL = "https://aman93578.pythonanywhere.com/"
const val ISOK = 200
const val GET_IMG_URL = "https://aman93578.pythonanywhere.com/getImg/"

fun getFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "temp_image.jpg") // Temporary file in cache

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
    return file
}