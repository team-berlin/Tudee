package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
    shape: Shape = ButtonDefaults.DefaultShape,
    contentPadding: PaddingValues = ButtonDefaults.DefaultPadding,
    buttonColors: ButtonColors = ButtonColors.Companion.primary(state),
    content: @Composable RowScope.() -> Unit
) {
    val backgroundModifier = when {
        buttonColors.backgroundColor != null -> Modifier.Companion.background(
            color = buttonColors.backgroundColor,
            shape = shape
        )

        buttonColors.backgroundBrush != null -> Modifier.Companion.background(
            brush = buttonColors.backgroundBrush,
            shape = shape
        )

        else -> Modifier.Companion
    }
    DefaultButton(
        onClick = onClick,
        modifier = modifier,
        state = state,
        type = ButtonType.PRIMARY,
        enabled = enabled,
        contentPadding = contentPadding,
        contentColor = buttonColors.contentColor,
        backgroundModifier = backgroundModifier,
        content = content
    )
}