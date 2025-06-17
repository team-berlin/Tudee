package com.example.tudee.presentation.screen.onboarding

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter

data class OnBoardingPageUiModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val image: Painter,
)

