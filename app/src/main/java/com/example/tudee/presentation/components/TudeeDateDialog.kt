package com.example.tudee.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.buttons.TextButton
import com.example.tudee.utils.convertMillisToDate
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeDateDialog(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
    onClear: () -> Unit
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    var dateState =  rememberDatePickerState(
        initialSelectedDateMillis = today.toEpochDays() * 86_400_000L

    )
    val formattedDate = remember(dateState.selectedDateMillis) {
        dateState.selectedDateMillis?.let {
            convertMillisToDate(it, "EEE, MMM d")
        }
    }
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = TudeeTheme.color.surface
            , titleContentColor = TudeeTheme.color.textColors.title
        ),

        )
    {
        DatePicker(
            state = dateState,
            headline = {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = formattedDate ?: stringResource(id = R.string.select_date),
                    color = TudeeTheme.color.textColors.title
                )
            },
            colors = DatePickerDefaults.colors(
                navigationContentColor =TudeeTheme.color.textColors.title ,
                headlineContentColor = TudeeTheme.color.textColors.title,
                titleContentColor = TudeeTheme.color.textColors.title,
                weekdayContentColor = TudeeTheme.color.textColors.title,
                dayContentColor=TudeeTheme.color.textColors.title,
                containerColor = TudeeTheme.color.statusColors.greenAccent ,
                selectedDayContainerColor = TudeeTheme.color.primary,
                selectedDayContentColor = TudeeTheme.color.textColors.onPrimary,
                todayContentColor = TudeeTheme.color.primary,
                todayDateBorderColor = TudeeTheme.color.primary,

            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 40.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.clickable {
                    dateState.selectedDateMillis = null
                    onClear()
                },
                text = stringResource(R.string.date_picker_clear_button_text),
                color = TudeeTheme.color.primary,
                style = TudeeTheme.textStyle.label.large
            )

            Row(horizontalArrangement = Arrangement.spacedBy(35.dp)) {

                Text(
                    modifier = Modifier.clickable { onDismiss() },
                    text = stringResource(R.string.date_picker_cancel_button_text),
                    color = TudeeTheme.color.primary,
                    style = TudeeTheme.textStyle.label.large
                )

                Text(
                    modifier = Modifier.clickable {
                        onConfirm(dateState.selectedDateMillis)
                        onDismiss()
                    },
                    text = stringResource(R.string.date_picker_ok_button_text),
                    color = TudeeTheme.color.primary,
                    style = TudeeTheme.textStyle.label.large
                )

            }
        }
    }
}

@Preview
@Composable
private fun TudeeDateDialogPreview() {
    TudeeDateDialog({ _ -> }, {}, {})
}