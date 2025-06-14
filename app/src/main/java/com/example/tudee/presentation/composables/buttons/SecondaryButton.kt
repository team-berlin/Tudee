package com.example.tudee.presentation.composables.buttons

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColor
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
    shape: Shape = ButtonDefaults.defaultShape,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    buttonColors: ButtonColors = ButtonDefaults.colors(),
    content: @Composable RowScope.() -> Unit
) {
    val backgroundColor = androidx.compose.ui.graphics.Color.Transparent
    val contentColor = when (state) {
        ButtonState.IDLE -> TudeeTheme.color.primary
        ButtonState.LOADING -> TudeeTheme.color.primary
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
private fun SecondaryButtonPreview() {
    SecondaryButton(
        onClick = {},
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview
@Composable
private fun SecondaryButtonLoadingPreview() {
    SecondaryButton(
        onClick = {},
        state = ButtonState.LOADING,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview
@Composable
private fun SecondaryButtonDisabledPreview() {
    SecondaryButton(
        onClick = {},
        state = ButtonState.DISABLED,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview
@Composable
private fun SecondaryButtonErrorPreview() {
    SecondaryButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}
