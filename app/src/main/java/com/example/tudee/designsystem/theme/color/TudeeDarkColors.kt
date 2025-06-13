package com.example.tudee.designsystem.theme.color

import androidx.compose.ui.graphics.Color

val TudeeDarkColors = TudeeColors(
    primary = Color(0xFF3090BF),
    secondary = Color(0xFFF49061),
    primaryVarient = Color(0xFF05202E),
    primaryGradient = listOf(Color(0xFF3DB6F2), Color(0xFF3A9CCD)),
    textColors = TextColors(
        title = Color(0xDEFFFFFF),
        body = Color(0x99FFFFFF),
        hint = Color(0x61FFFFFF),
        stroke = Color(0x1FFFFFFF),
        surfaceLow = Color(0xFF020108),
        surface = Color(0xFF0D0C14),
        surfaceHigh = Color(0xFF0F0E19),
        onPrimary = Color(0xDEFFFFFF),
        onPrimaryCaption = Color(0xB3FFFFFF),
        onPrimaryCard = Color(0x29060414),
        onPrimaryStroke = Color(0x99242424),
        disable = Color(0xFF1D1E1F),
    ),
    statusColors = StatusColors(
        pinkAccent = Color(0xFFF4869A),
        yellowAccent = Color(0xFFF2C849),
        greenAccent = Color(0xFF76C499),
        purpleAccent = Color(0xFF9887F5),
        error = Color(0xFFE55C5C),
        overlay = Color(0x5202151E),
        emojiTint = Color(0xDE1F1F1F),
        yellowVariant = Color(0xFF1F1E1C),
        greenVariant = Color(0xFF1C1F1D),
        purpleVariant = Color(0xFF1C1A33),
        errorVariant = Color(0xFF1F1111)
    )
)