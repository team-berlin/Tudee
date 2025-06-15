package com.example.tudee.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    leadingComposable: (@Composable () -> Unit)? = null,
    title: String? = null,
    titleColor: Color = TudeeTheme.color.textColors.title,
    titleStyle: TextStyle = TudeeTextStyle.title.medium,
    trailingComposable: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        leadingComposable?.invoke()

        if (title != null)
            Text(
                text = title,
                style = titleStyle,
                color = titleColor,
            )

        Spacer(modifier = Modifier.weight(1f))

        trailingComposable?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
private fun BackButtonPreview() {
    TudeeTheme {
        TopAppBar(
            leadingComposable = {
                IconButton(onClick = { /* Handle back action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Back",
                        modifier = Modifier.size(40.dp)
                    )
                }
            },
            title = "Tasks",
            trailingComposable = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "Settings",
                    modifier = Modifier.size(40.dp)
                )
            }
        )
    }
}