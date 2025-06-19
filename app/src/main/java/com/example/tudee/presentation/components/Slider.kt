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
import com.example.tudee.ui.home.viewmodel.SliderUiState

@Composable
fun TudeeSlider(
    sliderUiState: SliderUiState,
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
                sliderUiState.cardTitle?.let {
                    Text(
                        text = stringResource(it),
                        color = titleColor,
                        style = titleStyle,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                sliderUiState.cardEmoji?.let {
                    Image(
                        painter = painterResource(it),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            sliderUiState.cardDescription?.let {
                Text(
                    text = stringResource(it),
                    color = descriptionTextColor,
                    style = descriptionTextStyle,
                )
            }
        }
        sliderUiState.cardImage?.let {
            Image(
                painter =painterResource(it),
                contentDescription = null,
                modifier = Modifier
                    .width(76.dp)
                    .height(92.dp)
            )
        }
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
            sliderUiState = SliderUiState(),
        )
    }
}