package com.example.tudee.designsystem.theme.color

import androidx.compose.ui.graphics.Color

data class TudeeColors(
    val primary: Color,
    val secondary: Color,
    val primaryVariant: Color,
    val primaryGradient: List<Color>,
    val textColors: TextColors,
    val statusColors: StatusColors,
)

data class TextColors(
    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color,
    val surfaceLow: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val onPrimary: Color,
    val onPrimaryCaption: Color,
    val onPrimaryCard: Color,
    val onPrimaryStroke: Color,
    val disable: Color,
)

data class StatusColors(
    val pinkAccent: Color,
    val yellowAccent: Color,
    val greenAccent: Color,
    val purpleAccent: Color,
    val error: Color,
    val overlay: Color,
    val emojiTint: Color,
    val yellowVariant: Color,
    val greenVariant: Color,
    val purpleVariant: Color,
    val errorVariant: Color,
    val blackBlur: Color
)