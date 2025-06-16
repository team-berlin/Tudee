package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme

data class ButtonColors(
    val backgroundColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val backgroundGradient: Brush? = null,
    val disabledBackgroundColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val errorBackgroundColor: Color = Color.Unspecified,
    val errorContentColor: Color = Color.Unspecified,
)

object ButtonDefaults {
    val defaultHeight: Dp = 56.dp
    val defaultFabSize: Dp = 64.dp
    val defaultShape: RoundedCornerShape = CircleShape
    val defaultPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    val defaultFabPadding: PaddingValues = PaddingValues(horizontal = 18.dp, vertical = 18.dp)

    @Composable
    fun colors() = ButtonColors(
        backgroundColor = TudeeTheme.color.primary,
        contentColor = TudeeTheme.color.textColors.onPrimary,
        disabledBackgroundColor = TudeeTheme.color.disable,
        disabledContentColor = TudeeTheme.color.stroke,
        errorBackgroundColor = TudeeTheme.color.statusColors.errorVariant,
        errorContentColor = TudeeTheme.color.statusColors.error,
    )
}