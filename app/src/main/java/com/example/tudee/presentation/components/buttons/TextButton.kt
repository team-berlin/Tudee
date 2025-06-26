package com.example.tudee.presentation.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun TextButton(
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
            backgroundColor = Color.Transparent,
            errorBackgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            contentColor = TudeeTheme.color.primary,
            disabledContentColor =TudeeTheme.color.disable ,
            errorContentColor = TudeeTheme.color.statusColors.error,
        ),
        shape = shape,
        content = content
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun TextButtonPreview() {
    TextButton(
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
private fun TextButtonLoadingPreview() {
    TextButton(
        onClick = {},
        state = ButtonState.LOADING,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TextButtonDisabledPreview() {
    TextButton(
        onClick = {},
        state = ButtonState.DISABLED,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun TextButtonErrorPreview() {
    TextButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}
