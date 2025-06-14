package com.example.tudee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle

@Composable
fun BackButtonWithStartAndEndComposables(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 16.dp),
    startComposable: (@Composable () -> Unit)? = null,
    title: String? = "Tasks",
    titleColor: Color = TudeeTheme.color.textColors.title,
    titleStyle: TextStyle = TudeeTextStyle.title.medium,
    subTitle: String? = null,
    subTitleColor: Color = TudeeTheme.color.textColors.hint,
    subTitleStyle: TextStyle = TudeeTextStyle.label.small,
    endComposable: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        startComposable?.invoke()
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            if (title != null)
                Text(
                    text = title,
                    style = titleStyle,
                    color = titleColor,
                )
            if (subTitle != null) Text(
                text = subTitle,
                style = subTitleStyle,
                color = subTitleColor
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        endComposable?.invoke()

    }
}

@Preview(showBackground = true)
@Composable
private fun BackButtonPreview() {
    TudeeTheme {
        BackButtonWithStartAndEndComposables(
            startComposable = {
                IconButton(onClick = { /* Handle back action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_button),
                        contentDescription = "Back",
                        modifier = Modifier.size(40.dp)
                    )
                }
            },
            title = "Tasks",
            subTitle = "Today",
            endComposable = {
                Icon(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = "Settings",
                    modifier = Modifier.size(40.dp)
                )
            }
        )
    }
}