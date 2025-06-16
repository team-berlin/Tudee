package com.example.tudee.presentation.composables.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
    shape: Shape = ButtonDefaults.defaultShape,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    buttonColors: ButtonColors = ButtonDefaults.colors(),
    content: @Composable RowScope.() -> Unit
) {
    DefaultButton(
        onClick = onClick,
        modifier = modifier,
        state = state,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = buttonColors.copy(
            backgroundGradient = Brush.horizontalGradient(
                TudeeTheme.color.primaryGradient
            )
        ),
        shape = shape,
        content = content
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(
        onClick = {},
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonLoadingPreview() {
    PrimaryButton(
        onClick = {},
        state = ButtonState.LOADING,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonDisabledPreview() {
    PrimaryButton(
        onClick = {},
        state = ButtonState.DISABLED,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonErrorPreview() {
    PrimaryButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}
