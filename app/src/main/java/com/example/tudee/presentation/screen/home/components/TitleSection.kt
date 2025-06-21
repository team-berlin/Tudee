package com.example.tudee.presentation.screen.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun TitleSection(
    statusTitle: String,
    numberOfElement: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter = painterResource(R.drawable.arrow),
    statusTitleColor: Color = TudeeTheme.color.textColors.title,
    numberOfElementColor: Color = TudeeTheme.color.textColors.body,
    iconColor: Color = TudeeTheme.color.textColors.body
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = statusTitle,
            style = TudeeTheme.textStyle.title.large,
            color = statusTitleColor,

            )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .background(
                    color = TudeeTheme.color.surfaceHigh,
                    shape = RoundedCornerShape(100.dp)
                )
                .clickable(onClick = onClick),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 2.dp,
                    top = 6.dp,
                    bottom = 6.dp
                ),
                text = numberOfElement,
                color = numberOfElementColor,
                style = TudeeTheme.textStyle.label.small,
            )
            Icon(
                painter = icon,
                contentDescription = "arrow icon",
                tint = iconColor,
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                    .graphicsLayer(scaleX = if (LocalLayoutDirection.current == LayoutDirection.Rtl) -1f else 1f)
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = false, locale = "ar")
@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = false)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TitleSectionPreview() {
    TudeeTheme {
        TitleSection(
            statusTitle = stringResource(R.string.in_progress),
            numberOfElement = "12",
            onClick = {}
        )
    }
}