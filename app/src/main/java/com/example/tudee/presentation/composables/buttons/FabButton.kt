package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun FabButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
    shape: Shape = ButtonDefaults.defaultShape,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    buttonColors: ButtonColors = ButtonDefaults.colors(),
    content: @Composable RowScope.() -> Unit
) {
    val backgroundColor = when (state) {
        ButtonState.IDLE -> TudeeTheme.color.primary
        ButtonState.LOADING -> TudeeTheme.color.primary
        ButtonState.DISABLED -> TudeeTheme.color.textColors.disable
        ButtonState.ERROR -> TudeeTheme.color.statusColors.errorVariant
    }
    val contentColor = when (state) {
        ButtonState.IDLE -> TudeeTheme.color.textColors.onPrimary
        ButtonState.LOADING -> TudeeTheme.color.textColors.onPrimary
        ButtonState.DISABLED -> TudeeTheme.color.textColors.stroke
        ButtonState.ERROR -> TudeeTheme.color.statusColors.error

    }
    DefaultButton(
        onClick = onClick,
        modifier = modifier,
        state = state,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = buttonColors.copy(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = shape,
        content = content
    )
}

@Preview
@Composable
private fun FabButtonPreview() {
    FabButton(
        onClick = {},
        content = {}
    )
}

@Preview
@Composable
private fun FabButtonLoadingPreview() {
    FabButton(
        onClick = {},
        state = ButtonState.LOADING,
        content = {}
    )
}

@Preview
@Composable
private fun FabButtonDisabledPreview() {
    FabButton(
        onClick = {},
        state = ButtonState.DISABLED,
        content = {}
    )
}

@Preview
@Composable
private fun FabButtonErrorPreview() {
    FabButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {}
    )
}
