package com.example.tudee.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.utils.clickWithRipple

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TudeeDayCard(
    modifier: Modifier = Modifier,
    dayOfMonth: String,
    dayOfWeek: String,
    dayOfMonthTextColor: Color,
    dayOfWeekTextColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickWithRipple {
                onClick()
            }
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayOfMonth,
            style = TudeeTheme.textStyle.title.medium,
            color = dayOfMonthTextColor
        )
        Text(
            text = dayOfWeek,
            style = TudeeTheme.textStyle.body.small,
            modifier = Modifier.padding(top = 2.dp),
            color = dayOfWeekTextColor
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DayCardPreview() {
    TudeeTheme {
        val gradientBackgroundColor = Brush.linearGradient(
            colors = TudeeTheme.color.primaryGradient,
            start = Offset(0f, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY)
        )

        val isSelected = true

        val dayOfMonthTextColor =
            if (isSelected)
                TudeeTheme.color.textColors.onPrimary
            else
                TudeeTheme.color.textColors.body

        val dayOfWeekTextColor =
            if (isSelected)
                TudeeTheme.color.textColors.onPrimaryCaption
            else
                TudeeTheme.color.textColors.hint

        val modifier = if (isSelected) {
            Modifier.background(
                gradientBackgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
        } else {
            Modifier
                .background(
                    TudeeTheme.color.surface,
                    shape = RoundedCornerShape(16.dp)
                )
        }
        TudeeDayCard(
            modifier = modifier,
            dayOfMonth = "14",
            dayOfWeek = "SAT",
            dayOfMonthTextColor = dayOfMonthTextColor,
            dayOfWeekTextColor = dayOfWeekTextColor,
            onClick = {}
        )
    }
}
