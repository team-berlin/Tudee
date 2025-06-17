package com.example.tudee.presentation.screen.onboarding

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter

data class OnBoardingPageUiModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val image: Painter,
)

enum class Pages(val page: Int) {
    FistPage(0),
    SecondPage(1),
    ThirdPage(2)
}
