package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
    contentPadding: PaddingValues = ButtonDefaults.DefaultPadding,
    buttonColors: ButtonColors = ButtonColors.Companion.text(state),
    content: @Composable RowScope.() -> Unit
) {
    DefaultButton(
        onClick = onClick,
        modifier = modifier,
        state = state,
        type = ButtonType.TEXT,
        enabled = enabled,
        contentPadding = contentPadding,
        contentColor = buttonColors.contentColor,
        content = content
    )
}