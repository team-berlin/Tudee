package com.example.tudee.components

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
    iconRes: Int,
    iconBackgroundColor: Color,
    iconTint: Color = TudeeTheme.color.statusColors.error,
    contentColor: Color = TudeeTheme.color.textColors.body,
) {
    val textStyles = TudeeTheme.textStyle
    val colors =  TudeeTheme.color.textColors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
            )
            .background(colors.surfaceHigh, RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconBackgroundColor, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "snack bar icon",
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = message,
            style = textStyles.body.medium.copy(color = contentColor),
            textAlign = TextAlign.Start
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SnackBarExamplePreview() {
    SnackBarComponent(
        message = stringResource(R.string.snackBar_success_message),
        iconRes = R.drawable.ic_successfully,
        iconBackgroundColor = TudeeTheme.color.statusColors.greenVariant,
        iconTint = TudeeTheme.color.statusColors.greenAccent
    )
}
