package com.example.tudee.presentation.screens.home.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun NoTask(
    backgroundColor: Color = TudeeTheme.color.surfaceHigh,
    textStyle: TextStyle = TudeeTheme.textStyle.title.small,
    textColor: Color = TudeeTheme.color.textColors.body,
    hintColor: Color = TudeeTheme.color.textColors.hint,
    modifier: Modifier = Modifier
) {

    Row(
        modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surface)

    )
    {
        Box(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(
                            topEnd = 16.dp,
                            topStart = 16.dp,
                            bottomEnd = 2.dp,
                            bottomStart = 16.dp,
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                    text = stringResource(R.string.no_tasks_for_today),
                    style = textStyle,
                    color = textColor
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 8.dp, bottom = 8.dp),
                    text = stringResource(R.string.tap_the_button_to_add_your) +
                            stringResource(R.string.first_one),
                    style = textStyle,
                    color = hintColor
                )
            }



            TaskCardImage(modifier = Modifier.align(Alignment.BottomEnd))

        }
    }
}

@Composable
fun TaskCardImage(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .padding(end = 5.dp, top = 12.dp, bottom = 4.dp)
                .size(144.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.delete_bot_omage_container),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )

        Box(
            Modifier.align(Alignment.BottomEnd)
        ) {
            Image(
                modifier = Modifier
                    .size(136.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(R.drawable.image_blue_overlay),
                contentScale = ContentScale.Crop,
                contentDescription = ""

            )

        }
        Image(
            modifier = Modifier
                .size(height = 100.dp, width = 107.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.delete_task_bot),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Box(
            Modifier
                .size(136.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 6.dp, top = 53.dp)
                    .size(width = 23.dp, height = 34.dp),
                painter = painterResource(R.drawable.threedots),
                contentDescription = "",
                tint = TudeeTheme.color.surfaceHigh
            )
        }
    }
}


@Preview(
    showBackground = true, device = "spec:width=360dp,height=850.9dp,dpi=440",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)

@Preview(showBackground = true, device = "spec:width=360dp,height=850.9dp,dpi=440")
@Composable
fun NoTaskPreview() {
    TudeeTheme {
        NoTask()
    }
}





