package com.example.tudee.presentation.screen.onboarding

import androidx.compose.ui.graphics.painter.Painter

data class OnBoardingPageUiModel(
    val title: String,
    val description: String,
    val image: Painter,
)

enum class Pages(val page: Int) {
    FirstPage(0),
    SecondPage(1),
    ThirdPage(2)
}
