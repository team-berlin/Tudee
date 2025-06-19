package com.example.tudee.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.SnackBarComponent
import com.example.tudee.presentation.viewmodel.uistate.SnackBar
import com.example.tudee.presentation.viewmodel.uistate.TaskBottomSheetState

@Composable
fun TaskSnackBar(
    isSuccess: Boolean,
    modifier: Modifier = Modifier,
) {
        val colors = TudeeTheme.color.statusColors
        val config = if (isSuccess) {
            SnackBar(
                message = R.string.snack_bar_success_message,
                icon = R.drawable.ic_success,
                description = "snack bar icon",
                backgroundColor = colors.greenVariant,
                tint = colors.greenAccent
            )
        } else {
            SnackBar(
                message = R.string.snack_bar_error_message,
                icon = R.drawable.ic_error,
                description = "error icon",
                backgroundColor = colors.error,
                tint = colors.error
            )
        }
        Box(
            modifier = modifier
                .background(config.backgroundColor, RoundedCornerShape(16.dp))
                .zIndex(1f)
        ) {
            SnackBarComponent(
                modifier = Modifier.fillMaxWidth(),
                message = stringResource(config.message),
                iconPainter = painterResource(config.icon),
                iconDescription = config.description,
                iconBackgroundColor = config.backgroundColor,
                iconTint = config.tint
            )
        }
    
}