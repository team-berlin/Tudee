@file:JvmName("DefaultButtonKt")

package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
    shape: Shape = ButtonDefaults.DefaultShape,
    contentPadding: PaddingValues = ButtonDefaults.DefaultPadding,
    buttonColors: ButtonColors = ButtonColors.secondary(state),
    content: @Composable RowScope.() -> Unit
) {
    DefaultButton(
        onClick = onClick,
        modifier = modifier,
        state = state,
        type = ButtonType.SECONDARY,
        enabled = enabled,
        contentPadding = contentPadding,
        contentColor = buttonColors.contentColor,
        borderModifier = Modifier.border(
            width = 1.dp,
            color = buttonColors.borderColor ?: Color.Transparent,
            shape = shape
        ),
        content = content
    )
}

