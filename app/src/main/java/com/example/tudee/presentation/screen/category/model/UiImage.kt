package com.example.tudee.presentation.screen.category.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import com.example.tudee.presentation.utils.predefinedCategories
import com.example.tudee.presentation.utils.toCategoryIcon
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
                },
                imageLoader = LocalContext.current.imageLoader
            )
        }
    }

    fun asString(): String = when (this) {
        is Drawable -> resId.toString()
        is External -> uri
    }
}

fun String.toUiImage(): UiImage {
    return if (this in predefinedCategories.map { it.image }) {
        UiImage.Drawable(this.toCategoryIcon())
    } else {
        UiImage.External(this)
    }
}

fun UiImage?.isNotNull(): Boolean {
    return this != null
}