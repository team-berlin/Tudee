package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.TopAppBar

@Composable
 fun TaskScreenTopAppBar() {
    TopAppBar(
        modifier = Modifier.background(TudeeTheme.color.surfaceHigh),
        title = stringResource(R.string.tasks),
        showBackButton = false
    )
}
