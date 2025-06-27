package com.example.tudee.utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

/**
 * Copies an image from a given content URI to the app's internal storage.
 *
 * @param context The application context.
 * @param originalUri The content URI of the original image.
 * @return The URI of the newly created copy, or null if it fails.
 */
suspend fun saveImageToInternalStorage(context: Context, originalUri: Uri): Uri? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(originalUri)

            val fileName = "copied_image_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            file.toUri()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}