package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.component.ThemeSwitcher
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = false,
    onThemeChanged: (Boolean) -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconWithTitleAndDescription(
            imagePainter = painterResource(id = R.drawable.tudee),
            titleImagePainter = painterResource(id = R.drawable.app_name),
            description = stringResource(id = R.string.tudee_description)
        )
        Spacer(modifier = Modifier.weight(1f))

        ThemeSwitcher(
            isChecked = isDarkMode,
            onCheckedChanged = onThemeChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    TudeeTheme {
        AppBar()
    }
}