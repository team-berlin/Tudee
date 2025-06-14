package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme

object ButtonDefaults {
    val DefaultHeight = 56.dp
    val DefaultPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    val DefaultShape = CircleShape
    val DefaultIconSpacing = 8.dp
    val primaryContent @Composable get() = TudeeTheme.color.textColors.onPrimary.copy(alpha = 0.87f)
    val PrimaryGradient @Composable get() = TudeeTheme.color.primaryGradient
    val primaryColor @Composable get() = TudeeTheme.color.primary
    val DisabledBackground @Composable get() = TudeeTheme.color.textColors.disable
    val ErrorBackground @Composable get() = TudeeTheme.color.statusColors.errorVariant
    val ErrorContent @Composable get() = TudeeTheme.color.statusColors.error
    val Stroke @Composable get() = TudeeTheme.color.textColors.stroke
    val BorderColor @Composable get() = TudeeTheme.color.textColors.disable
}