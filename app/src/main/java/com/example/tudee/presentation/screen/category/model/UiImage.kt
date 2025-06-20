package com.example.tudee.presentation.screen.category.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.core.text.isDigitsOnly
import coil3.compose.rememberAsyncImagePainter
import java.io.File

sealed class UiImage {
    data class Drawable(@DrawableRes val resId: Int) : UiImage()
    data class External(val uri: String) : UiImage()

    @Composable
    fun asPainter(): Painter {
        return when (this) {
            is Drawable -> painterResource(id = resId)
            is External -> rememberAsyncImagePainter(
                model = when {
                    uri.startsWith("content://") || uri.startsWith("file://") -> uri
                    uri.startsWith("/") -> File(uri)
                    else -> uri
                }
            )
        }
    }

    fun asString(): String = when (this) {
        is Drawable -> resId.toString()
        is External -> uri
    }
}

fun String.toUiImage(): UiImage {
    return if (isDigitsOnly()) {
        UiImage.Drawable(toInt())
    } else {
        UiImage.External(this)
    }
}