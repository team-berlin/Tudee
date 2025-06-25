package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.SnackBarComponent
import kotlinx.coroutines.delay

@Composable
fun SnackBarSection(
    isSnackBarVisible: Boolean, hideSnackBar: () -> Unit = {},showSnackBar :Boolean = false
) {
    LaunchedEffect(isSnackBarVisible) {
        delay(2000)
        hideSnackBar
        showSnackBar
    }

    AnimatedVisibility(
        visible = isSnackBarVisible, enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ) + fadeIn(),

        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessMedium, dampingRatio = Spring.DampingRatioNoBouncy
            )
        ) + fadeOut()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .offset(y = 56.dp)
                .padding(horizontal = 16.dp)
                .zIndex(1f)
        ) {
            SnackBarComponent(
                message = stringResource(R.string.snack_bar_success_message),
                iconPainter = painterResource(R.drawable.ic_success),
                iconTint = TudeeTheme.color.statusColors.greenAccent,
                iconBackgroundColor = TudeeTheme.color.statusColors.greenVariant
            )
        }
    }
}






