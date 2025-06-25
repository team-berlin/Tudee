package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Preview(showBackground = true)
@Composable
fun NotTaskForTodayDialogue() {
    Box(
        Modifier
            .background(TudeeTheme.color.surface)
            .width(360.dp)
            .height(153.dp)
            .padding(end = 20.dp)
        ,
    ) {
        Column(
            Modifier
                .padding(start = 10.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topEnd = 16.dp,
                        topStart = 16.dp,
                        bottomEnd = 2.dp,
                        bottomStart = 16.dp,
                    )
                )
                .offset(y = (-4).dp)
                .zIndex(1f)
                .width(203.dp)
                .background(TudeeTheme.color.surfaceHigh)
                .padding(start = 12.dp, end = 10.dp)
                .padding(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)

        ) {
            Text(
                text = stringResource(id = R.string.no_tasks),
                style = TudeeTheme.textStyle.title.small,
                color = TudeeTheme.color.textColors.body,

                )
            Text(
                text = stringResource(R.string.add_first_task_hint),
                style = TudeeTheme.textStyle.body.small,
                color = TudeeTheme.color.textColors.hint,
            )
        }
        Box(
            Modifier.align(Alignment.BottomEnd)
        ) {
                Image(
                    modifier = Modifier
                        .padding(end = 5.dp)
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
                Image(
                    modifier = Modifier
                        .padding(start = 6.dp, top =4.dp )
                        .size(width = 23.dp, height = 34.dp)

                        .align(Alignment.CenterStart),
                    painter = painterResource(R.drawable.hint_indicator),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }
        }
    }
}