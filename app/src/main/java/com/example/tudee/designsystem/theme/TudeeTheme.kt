package com.example.tudee.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.example.tudee.designsystem.theme.color.LocalTudeeColors
import com.example.tudee.designsystem.theme.color.TudeeColors
import com.example.tudee.designsystem.theme.color.TudeeDarkColors
import com.example.tudee.designsystem.theme.color.TudeeLightColors
import com.example.tudee.designsystem.theme.textstyle.LocalTudeeTextStyle
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyles

@Composable
fun TudeeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) TudeeDarkColors else TudeeLightColors
    CompositionLocalProvider(LocalTudeeColors provides colors) {
        LocalTudeeTextStyle provides TudeeTextStyle
        content()
    }
}

object TudeeTheme {
    val color: TudeeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalTudeeColors.current
    val textStyle: TudeeTextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalTudeeTextStyle.current
}