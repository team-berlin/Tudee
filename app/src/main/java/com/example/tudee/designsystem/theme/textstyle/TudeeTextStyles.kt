package com.example.tudee.designsystem.theme.textstyle

import androidx.compose.ui.text.TextStyle

data class TudeeTextStyles(
    val headline: SizedTextStyle,
    val title: SizedTextStyle,
    val body: SizedTextStyle,
    val label: SizedTextStyle,
)

data class SizedTextStyle(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
)