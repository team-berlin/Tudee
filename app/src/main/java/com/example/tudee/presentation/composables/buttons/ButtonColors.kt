package com.example.tudee.presentation.composables.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class ButtonColors(
    val contentColor: Color,
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
    val backgroundBrush: Brush? = null
) {
    companion object {
        @Composable
        fun primary(state: ButtonState): ButtonColors = when (state) {
            ButtonState.IDLE, ButtonState.LOADING -> ButtonColors(
                contentColor = ButtonDefaults.primaryContent,
                backgroundBrush = Brush.horizontalGradient(
                    colors = ButtonDefaults.PrimaryGradient
                )
            )

            ButtonState.DISABLED -> ButtonColors(
                contentColor = ButtonDefaults.Stroke,
                backgroundColor = ButtonDefaults.DisabledBackground
            )

            ButtonState.ERROR -> ButtonColors(
                contentColor = ButtonDefaults.ErrorContent,
                backgroundColor = ButtonDefaults.ErrorBackground
            )
        }

        @Composable
        fun secondary(state: ButtonState): ButtonColors = when (state) {
            ButtonState.IDLE, ButtonState.LOADING -> ButtonColors(
                contentColor = ButtonDefaults.primaryColor,
                borderColor = ButtonDefaults.Stroke
            )

            ButtonState.DISABLED -> ButtonColors(
                contentColor = ButtonDefaults.Stroke,
                borderColor = ButtonDefaults.DisabledBackground
            )

            ButtonState.ERROR -> ButtonColors(
                contentColor = ButtonDefaults.ErrorContent,
                borderColor = ButtonDefaults.ErrorContent
            )
        }

        @Composable
        fun text(state: ButtonState): ButtonColors = when (state) {
            ButtonState.IDLE, ButtonState.LOADING -> ButtonColors(
                contentColor = ButtonDefaults.primaryColor
            )

            ButtonState.DISABLED -> ButtonColors(
                contentColor = ButtonDefaults.BorderColor
            )

            ButtonState.ERROR -> ButtonColors(
                contentColor = ButtonDefaults.ErrorContent
            )
        }

        @Composable
        fun fab(state: ButtonState): ButtonColors = when (state) {
            ButtonState.IDLE, ButtonState.LOADING -> ButtonColors(
                contentColor = Color.White,
                backgroundBrush = Brush.horizontalGradient(
                    colors = ButtonDefaults.PrimaryGradient
                )
            )

            ButtonState.DISABLED -> ButtonColors(
                contentColor = ButtonDefaults.Stroke,
                backgroundColor = ButtonDefaults.DisabledBackground
            )

            ButtonState.ERROR -> ButtonColors(
                contentColor = ButtonDefaults.ErrorContent,
                backgroundColor = ButtonDefaults.ErrorBackground
            )
        }
    }
}