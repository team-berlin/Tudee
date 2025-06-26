package com.example.tudee.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickWithRipple(isEnable: Boolean = true, onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember
        { MutableInteractionSource() },
        indication = rememberRipple(),
        onClick = onClick,
        enabled = isEnable
    )
}