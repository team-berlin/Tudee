package com.example.tudee.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.TudeeSlider
import com.example.tudee.presentation.screen.home.viewmodel.SliderUiState

@Composable
fun HomeOverviewCard(
    modifier: Modifier = Modifier,
    todayDate: String,
    month: String,
    year: String,
    tasksDoneCount: String,
    tasksTodoCount: String,
    tasksInProgressCount: String,
    sliderUiState: SliderUiState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = TudeeTheme.color.surfaceHigh, shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                tint = TudeeTheme.color.textColors.body,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.today, todayDate, month, year),
                style = TudeeTheme.textStyle.label.medium,
                color = TudeeTheme.color.textColors.body
            )
        }
        TudeeSlider(
            modifier = Modifier.padding(horizontal = 6.dp),
            sliderUiState = sliderUiState,
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            text = stringResource(R.string.overview),
            style = TudeeTheme.textStyle.title.large,
            color = TudeeTheme.color.textColors.title
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OverviewCard(
                modifier = Modifier.weight(1f),
                statusIcon = ImageVector.vectorResource(R.drawable.ic_done),
                numberOfTasks = tasksDoneCount,
                status = stringResource(R.string.done),
                cardColor = TudeeTheme.color.statusColors.greenAccent,
            )
            OverviewCard(
                modifier = Modifier.weight(1f),
                statusIcon = ImageVector.vectorResource(R.drawable.ic_in_progress),
                numberOfTasks = tasksInProgressCount,
                status = stringResource(R.string.in_progress),
                cardColor = TudeeTheme.color.statusColors.yellowAccent,
            )
            OverviewCard(
                modifier = Modifier.weight(1f),
                statusIcon = ImageVector.vectorResource(R.drawable.ic_to_do),
                numberOfTasks = tasksTodoCount,
                status = stringResource(R.string.todo),
                cardColor = TudeeTheme.color.statusColors.purpleAccent,
            )
        }
    }
}

@Composable
private fun OverviewCard(
    modifier: Modifier = Modifier,
    statusIcon: ImageVector,
    numberOfTasks: String,
    status: String,
    cardColor: Color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = cardColor, shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 44.dp)
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xffFFFFF).copy(0.12f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = Color(0xffFFFFF).copy(0.24f),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = TudeeTheme.color.textColors.onPrimary
                )
            }
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 12.dp),
                text = numberOfTasks,
                style = TudeeTheme.textStyle.headline.medium,
                maxLines = 1,
                color = TudeeTheme.color.textColors.onPrimary
            )
            Text(
                modifier = Modifier.padding(start = 12.dp, bottom = 12.dp),
                text = status,
                style = TudeeTheme.textStyle.label.small,
                maxLines = 1,
                color = TudeeTheme.color.textColors.onPrimaryCaption
            )
        }
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .graphicsLayer(scaleX = if (LocalLayoutDirection.current == LayoutDirection.Rtl) -1f else 1f),
            painter = painterResource(R.drawable.overview_card_background),
            contentDescription = null
        )
    }

}

@Preview(locale = "ar")
@Composable
private fun HomeOverviewCardPreview() {
    TudeeTheme {
        HomeOverviewCard(
            todayDate = "22",
            month = "Jun",
            year = "2025",
            tasksDoneCount = "2",
            tasksTodoCount = "16",
            tasksInProgressCount = "1",
            sliderUiState = SliderUiState()
        )
    }
}