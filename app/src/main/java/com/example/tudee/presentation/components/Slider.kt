package com.example.tudee.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun TudeeSlider(
    title: String,
    description: String,
    emoji: Painter,
    image: Painter,
    modifier: Modifier = Modifier,
    titleColor: Color = TudeeTheme.color.textColors.title,
    descriptionTextColor: Color = TudeeTheme.color.textColors.body,
    titleStyle: TextStyle = TudeeTheme.textStyle.title.small,
    descriptionTextStyle: TextStyle = TudeeTheme.textStyle.body.small
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
                .padding(end = 8.dp),
        ) {
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = title,
                    color = titleColor,
                    style = titleStyle,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Image(
                    painter = emoji,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = description,
                color = descriptionTextColor,
                style = descriptionTextStyle,
            )
        }
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .width(76.dp)
                .height(92.dp)
        )
    }
}

@Preview(
    name = "TudeeSlider Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF1E1E1E
)
@Preview(name = "TudeeSlider Light Mode")
@Composable
private fun TudeeSliderPreview() {
    TudeeTheme {
        TudeeSlider(
            title = stringResource(R.string.stay_working),
            description = stringResource(R.string.you_ve_completed_3_out_of_10_tasks_keep_going),
            emoji = painterResource(R.drawable.emoji_stay_working),
            image = painterResource(R.drawable.tudee_stay_working_or_nothing),
        )
    }
}