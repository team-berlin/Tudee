package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackButtonClicked: () -> Unit = {},
    title: String? = null,
    titleColor: Color = TudeeTheme.color.textColors.title,
    titleStyle: TextStyle = TudeeTextStyle.title.medium,
    trailingComposable: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showBackButton) IconButton(onClick = onBackButtonClicked) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier
                        .border(
                            1.dp, TudeeTheme.color.stroke, RoundedCornerShape(100.dp)
                        )
                        .padding(10.dp)
                        .size(20.dp),
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.back_button),
                    tint = TudeeTheme.color.textColors.body
                )
            }
        }
        title?.let {
            Text(
                text = title,
                style = titleStyle,
                color = titleColor,
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        trailingComposable?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
private fun BackButtonPreview() {
    TudeeTheme {
        TopAppBar(
            title = "Tasks", trailingComposable = {
                Icon(
                    modifier = Modifier
                        .border(
                            1.dp, TudeeTheme.color.stroke, RoundedCornerShape(100.dp)
                        )
                        .padding(10.dp)
                        .size(20.dp),
                    painter = painterResource(R.drawable.filter_icon),
                    contentDescription = stringResource(R.string.back_button),
                    tint = TudeeTheme.color.textColors.body
                )
            }
        )
    }
}