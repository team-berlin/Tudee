package com.example.tudee.presentation.onBoarding

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val image: Painter,
)

