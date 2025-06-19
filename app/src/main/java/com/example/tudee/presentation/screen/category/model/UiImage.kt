package com.example.tudee.presentation.screen.category.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

sealed class UiImage {
    data class Drawable(@DrawableRes val resId: Int) : UiImage()
    data class Url(val url: String) : UiImage()
}

@Composable
fun UiImage.asPainter(): Painter {
    return when (this) {
        is UiImage.Drawable -> painterResource(id = resId)
        is UiImage.Url -> TODO() //Handle URL images
    }
}