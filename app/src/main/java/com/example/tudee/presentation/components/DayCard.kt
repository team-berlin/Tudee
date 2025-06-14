package com.example.tudee.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import com.example.tudee.designsystem.theme.TudeeTheme
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    localDate: LocalDate
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val horizontalPadding = (screenWidth * 0.03f).dp
    val verticalPadding = (screenWidth * 0.02f).dp

    val gradientBackgroundColor = Brush.linearGradient(
        colors = TudeeTheme.color.primaryGradient,
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    val appliedModifier = if (isSelected) {
        modifier.background(
            gradientBackgroundColor,
            shape = RoundedCornerShape(16.dp)
        )
    } else {
        modifier.background(
            TudeeTheme.color.textColors.surface,
            shape = RoundedCornerShape(16.dp)
        )
    }

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

    Column(
        modifier = appliedModifier
            .padding(vertical = verticalPadding, horizontal = horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = localDate.dayOfMonth.toString(),
            style = TudeeTheme.textStyle.title.medium,
            color = dayOfMonthTextColor
        )

        Text(
            text = localDate.dayOfWeek.toString().take(3),
            style = TudeeTheme.textStyle.body.small,
            modifier = Modifier.padding(top = 2.dp),
            color = dayOfWeekTextColor
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DayCardPreview() {
    DayCard(isSelected = true, localDate = LocalDate.of(2025, 6, 14))
}