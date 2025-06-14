package com.example.tudee.presentation.composables.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.composables.SpinningProgressIndicatorLines

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    state: ButtonState = ButtonState.IDLE,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    shape: Shape = ButtonDefaults.defaultShape,
    colors: ButtonColors= ButtonDefaults.colors(),
    border: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .height(ButtonDefaults.defaultHeight)
            .clip(shape)
            .then(if(colors.backgroundGradient!=null) Modifier.background(colors.backgroundGradient,shape) else Modifier.background(colors.backgroundColor,shape))
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        val mergedStyle = LocalTextStyle.current.merge(colors.contentColor)
        CompositionLocalProvider(
            LocalContentColor provides colors.contentColor,
            LocalTextStyle provides mergedStyle,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(contentPadding)
            ) {
                content()
                AnimatedVisibility(state == ButtonState.LOADING) {
                    SpinningProgressIndicatorLines(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        size = 16.dp,
                        lineLength = 4.dp,
                        lineWidth = 3.dp,
                        color = colors.contentColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultButtonPreview() {
    DefaultButton(
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
private fun DefaultButtonLoadingPreview() {
    DefaultButton(
        onClick = {},
        state = ButtonState.LOADING,
        content = {}
    )
}

@Preview
@Composable
private fun DefaultButtonDisabledPreview() {
    DefaultButton(
        onClick = {},
        state = ButtonState.DISABLED,
        content = {}
    )
}

@Preview
@Composable
private fun DefaultButtonErrorPreview() {
    DefaultButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {}
    )
}
