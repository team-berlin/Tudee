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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.presentation.composables.SpinningProgressIndicatorLines

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    state: ButtonState = ButtonState.IDLE,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    shape: Shape = ButtonDefaults.defaultShape,
    colors: ButtonColors = ButtonDefaults.colors(),
    border: BorderStroke? = null,
    isFab: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor = when (state) {
        ButtonState.IDLE, ButtonState.LOADING -> colors.contentColor
        ButtonState.DISABLED -> colors.disabledContentColor
        ButtonState.ERROR -> colors.errorContentColor
    }
    val backgroundColor = when (state) {
        ButtonState.IDLE, ButtonState.LOADING -> colors.backgroundColor
        ButtonState.DISABLED -> colors.disabledBackgroundColor
        ButtonState.ERROR -> colors.errorBackgroundColor
    }
    Box(
        modifier = modifier
            .height(ButtonDefaults.defaultHeight)
            .then(if (isFab) Modifier.widthIn(min = ButtonDefaults.defaultHeight) else Modifier)
            .clip(shape)
            .then(
                if (colors.backgroundGradient != null) Modifier.background(
                    colors.backgroundGradient, shape
                ) else Modifier.background(backgroundColor, shape)
            )
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .clickable(enabled = enabled) { onClick() }, contentAlignment = Alignment.Center
    ) {
        val mergedStyle = LocalTextStyle.current.merge(contentColor)
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
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
                        modifier = Modifier.padding(start = if (state == ButtonState.LOADING && !isFab) 8.dp else 0.dp),
                        size = 16.dp,
                        lineLength = 4.dp,
                        lineWidth = 3.dp,
                        color = contentColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultButtonPreview() {
    DefaultButton(onClick = {}, content = {
        Text(
            text = "Button",
        )
    })
}

@Preview
@Composable
private fun DefaultButtonLoadingPreview() {
    DefaultButton(onClick = {}, state = ButtonState.LOADING, content = {
        Text(
            text = "Button",
        )
    })
}

@Preview
@Composable
private fun DefaultButtonDisabledPreview() {
    DefaultButton(onClick = {}, state = ButtonState.DISABLED, content = {
        Text(
            text = "Button",
        )
    })
}

@Preview
@Composable
private fun DefaultButtonErrorPreview() {
    DefaultButton(onClick = {}, state = ButtonState.ERROR, content = {
        Text(
            text = "Button",
        )
    })
}
