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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun NoTask(modifier: Modifier = Modifier) {

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)) {
        Box {
            Column(
                modifier = Modifier
                    .background(
                        color = TudeeTheme.color.surfaceHigh,
                        shape = RoundedCornerShape(12.dp)
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                    text = "No tasks for today!",
                    style = TudeeTheme.textStyle.title.small,
                    color = TudeeTheme.color.textColors.body
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                    text = "Tap the + button to add your \n" +
                            "first one.",
                    style = TudeeTheme.textStyle.body.small,
                    color = TudeeTheme.color.textColors.hint
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.BottomEnd
            ) {
                Icon(
                    painter = painterResource(R.drawable.background_tudee_no_task),
                    contentDescription = null,
                    tint = TudeeTheme.color.primary,
                    modifier = Modifier
                        .height(148.dp)
                        .width(149.dp)

                )
                Image(
                    painter = painterResource(R.drawable.tudee_no_task),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(107.dp)
                        .align(Alignment.BottomEnd)
                )
            }
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