package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme

data class ButtonColors(
    val backgroundColor: Color = Color.Unspecified,
    val contentColor: Color= Color.Unspecified,
    val backgroundGradient: Brush? =null ,
)
object ButtonDefaults {
    val defaultHeight: Dp = 56.dp
    val defaultShape: RoundedCornerShape = CircleShape
    val defaultPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    @Composable
    fun colors() = ButtonColors(
        backgroundColor = TudeeTheme.color.primary ,
        contentColor = TudeeTheme.color.textColors.onPrimary,
    )
}