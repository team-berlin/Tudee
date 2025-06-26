package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.TudeeDayCard
import com.example.tudee.presentation.screen.task_screen.ui_states.DateCardUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.DateUiState
import com.example.tudee.presentation.utils.clickWithRipple
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DateSection(
    listOfDateCardUiState: List<DateCardUiState>,
    datePickerUiState: DateUiState,
    onCalendarClicked: () -> Unit,
    onDayCardClicked: (Int) -> Unit,
    onPreviousArrowClicked: () -> Unit,
    onNextArrowClicked: () -> Unit,
    version: Int,
) {
    DataHeader(
        selectedMonth = datePickerUiState.selectedYearMonth.month.getDisplayName(
            TextStyle.SHORT, Locale.getDefault()
        ),
        selectedYear = datePickerUiState.selectedYear,
        onCalendarClicked = onCalendarClicked,
        onPreviousArrowClicked = onPreviousArrowClicked,
        onNextArrowClicked = onNextArrowClicked
    )
    DaysRow(
        listOfDateCardUiState = listOfDateCardUiState,
        onDayCardClicked = onDayCardClicked,
        version = version
    )
}

@Composable
fun DataHeader(
    selectedMonth: String,
    selectedYear: String,
    onCalendarClicked: () -> Unit,
    onPreviousArrowClicked: () -> Unit,
    onNextArrowClicked: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArrowButton(
            icon = painterResource(R.drawable.left_arrow),
            contentDescription = stringResource(R.string.previous_week_arrow_content_description),
            onClick = onPreviousArrowClicked
        )
        Column(
            modifier = Modifier
                .clip(CircleShape)
                .clickWithRipple(onClick = onCalendarClicked),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
             Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = "$selectedMonth, $selectedYear",
                    style = TudeeTheme.textStyle.label.medium,
                    color = TudeeTheme.color.textColors.body
                )
                Icon(
                    painter = painterResource(R.drawable.arrow_down),
                    tint = TudeeTheme.color.textColors.body,
                    contentDescription = stringResource(R.string.open_calendar_content_description),
                )

            }

        }

        ArrowButton(
            icon = painterResource(R.drawable.right_arrow),
            contentDescription = stringResource(R.string.next_week_arrow_content_description),
            onClick = onNextArrowClicked
        )
    }
}

@Composable
private fun ArrowButton(
    icon: Painter, contentDescription: String, onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(32.dp)
            .border(1.dp, TudeeTheme.color.stroke, shape = CircleShape)
            .clip(CircleShape)
            .clickWithRipple { onClick() }, contentAlignment = Alignment.Center
    ) {
        Icon(

            painter = icon,
            contentDescription = contentDescription,
            tint = TudeeTheme.color.textColors.body
        )
    }
}

@Composable
fun DaysRow(
    onDayCardClicked: (Int) -> Unit, listOfDateCardUiState: List<DateCardUiState>, version: Int
) {

    val listState = rememberLazyListState()

    val selectedIndex = listOfDateCardUiState.indexOfFirst { it.isSelected }.coerceAtLeast(0)
    LaunchedEffect(version) {
        if (selectedIndex > 0) listState.scrollToItem(selectedIndex - 1)
        else listState.scrollToItem(selectedIndex)
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(listOfDateCardUiState) { index, dateCard ->
            val dayOfMonthTextColor = if (dateCard.isSelected) TudeeTheme.color.textColors.onPrimary
            else TudeeTheme.color.textColors.body

            val dayOfWeekTextColor =
                if (dateCard.isSelected) TudeeTheme.color.textColors.onPrimaryCaption
                else TudeeTheme.color.textColors.hint

            val modifier = if (dateCard.isSelected) {
                Modifier
                    .size(width = 56.dp, height = 65.dp)
                    .background(
                        brush = (Brush.linearGradient(
                            colors = TudeeTheme.color.primaryGradient,
                            start = Offset(0f, 0f),
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        )),

                        shape = RoundedCornerShape(16.dp)
                    )
            } else {
                Modifier
                    .size(width = 56.dp, height = 65.dp)
                    .background(
                        TudeeTheme.color.surface, shape = RoundedCornerShape(16.dp)
                    )
            }
            TudeeDayCard(
                modifier = modifier,
                dayOfMonth = dateCard.dayNumber.toString(),
                dayOfWeek = dateCard.dayName,
                dayOfMonthTextColor = dayOfMonthTextColor,
                dayOfWeekTextColor = dayOfWeekTextColor,
                onClick = { onDayCardClicked(index) })
        }
    }

}
