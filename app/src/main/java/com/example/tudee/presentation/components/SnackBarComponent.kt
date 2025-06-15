package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun SnackBarComponent(
    modifier: Modifier = Modifier,
    message: String,
    icon: Painter,
    iconDescription: String = "",
    iconBackgroundColor: Color,
    iconTint: Color = TudeeTheme.color.statusColors.error,
    contentColor: Color = TudeeTheme.color.textColors.body,

) {
    val shadowColor = Color(0x1F000000)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = shadowColor
            )
            .background(TudeeTheme.color.textColors.surfaceHigh, RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = iconDescription,
            tint = iconTint,
            modifier = Modifier
                .size(40.dp)
                .background(iconBackgroundColor, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        )

        Text(
            text = message,
            style = TudeeTheme.textStyle.body.medium.copy(color = contentColor),
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SnackBarExamplePreview() {
    TudeeTheme {
        SnackBarComponent(
            message = stringResource(R.string.snack_bar_success_message),
            icon = painterResource(id = R.drawable.ic_success),
            iconDescription = "snack bar icon",
            iconBackgroundColor = TudeeTheme.color.statusColors.greenVariant,
            iconTint = TudeeTheme.color.statusColors.greenAccent
        )
    }
}
