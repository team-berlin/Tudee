package com.example.tudee.presentation.composables.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tudee.presentation.composables.SpinningProgressIndicatorLines

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion,
    enabled: Boolean = true,
    state: ButtonState = ButtonState.IDLE,
    type: ButtonType = ButtonType.PRIMARY,
    contentPadding: PaddingValues = ButtonDefaults.DefaultPadding,
    contentColor: Color = ButtonDefaults.primaryColor,
    backgroundModifier: Modifier = Modifier.Companion,
    borderModifier: Modifier = Modifier.Companion,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .height(ButtonDefaults.DefaultHeight)
            .then(backgroundModifier)
            .then(borderModifier)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Companion.Center
    ) {

        val mergedStyle = LocalTextStyle.current.merge(contentColor)
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides mergedStyle,
        ) {
            Row(
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.Companion.padding(contentPadding)
            ) {
                content()
                AnimatedVisibility(state == ButtonState.LOADING) {
                    Row {
                        if (type != ButtonType.FAB) {
                            Spacer(modifier = Modifier.Companion.width(ButtonDefaults.DefaultIconSpacing))
                        }
                        SpinningProgressIndicatorLines(
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
}