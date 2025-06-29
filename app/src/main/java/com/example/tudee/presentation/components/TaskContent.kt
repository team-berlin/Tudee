package com.example.tudee.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.PrimaryButton
import com.example.tudee.presentation.components.buttons.SecondaryButton
import com.example.tudee.presentation.screen.category.model.toUiImage
import com.example.tudee.presentation.screen.home.viewmodel.CategoryUiState
import com.example.tudee.presentation.screen.home.viewmodel.HomeActions
import com.example.tudee.presentation.screen.home.viewmodel.HomeUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskPriorityUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskUiState
import com.example.tudee.presentation.utils.clickWithRipple
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

enum class TaskContentMode {
    ADD,
    EDIT
}

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    categories: List<CategoryUiState>,
    onAction: (HomeActions) -> Unit,
    mode: TaskContentMode = TaskContentMode.ADD
) {

    Log.d("TaskContent", "Selected task category ID: ${state.taskUiState.taskCategory.id}")
    categories.forEachIndexed { index, category ->
        Log.d("TaskContent", "Category $index: title='${category.title}', id=${category.id}")
    }
    val priorities = listOf(
        TaskPriorityUiState.HIGH to Triple(
            stringResource(R.string.high),
            R.drawable.ic_priority_high,
            TudeeTheme.color.statusColors.pinkAccent
        ),
        TaskPriorityUiState.MEDIUM to Triple(
            stringResource(R.string.medium),
            R.drawable.ic_priority_medium,
            TudeeTheme.color.statusColors.yellowAccent
        ),
        TaskPriorityUiState.LOW to Triple(
            stringResource(R.string.low),
            R.drawable.ic_priority_low,
            TudeeTheme.color.statusColors.greenAccent
        )
    )

    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            item {
                Text(
                    text = stringResource(
                        if (mode == TaskContentMode.EDIT) R.string.edit_task_title else R.string.add_task_title
                    ),
                    style = TudeeTheme.textStyle.title.large,
                    color = TudeeTheme.color.textColors.title
                )
            }
            item {
                TudeeTextField(
                    modifier = Modifier.padding(top = 12.dp),
                    value = state.selectedTask.taskTitle,
                    onValueChange = { onAction(HomeActions.OnEditTaskTitleChanged(it)) },
                    placeholder = stringResource(R.string.task_title),
                    leadingContent = { isFocused ->
                        DefaultLeadingContent(
                            painter = painterResource(R.drawable.ic_notebook),
                            isFocused = isFocused
                        )
                    },
                    textStyle = TudeeTheme.textStyle.label.medium
                )
            }
            item {
                TudeeTextField(
                    textStyle = TudeeTheme.textStyle.body.medium,
                    value = state.selectedTask.taskDescription,
                    onValueChange = { onAction(HomeActions.OnEditTaskDescriptionChanged(it)) },
                    placeholder = stringResource(R.string.description),
                    singleLine = false,
                    modifier = Modifier
                        .height(168.dp)
                        .padding(top = 16.dp)
                )

            }
            item {
                var showDatePicker by remember { mutableStateOf(false) }

                TudeeTextField(
                    enabled = false,
                    modifier = Modifier.padding(top = 16.dp),
                    value = state.selectedTask.taskAssignedDate.toString(),
                    onValueChange = { },
                    placeholder = stringResource(R.string.set_due_date),
                    leadingContent = { isFocused ->
                        DefaultLeadingContent(
                            modifier = Modifier.clickWithRipple { showDatePicker = true },
                            painter = painterResource(R.drawable.ic_add_calendar),
                            isFocused = isFocused
                        )
                    }
                )


                if (showDatePicker) {
                    DatePickerDialogComponent(
                        onDateSelected = { selectedDate ->
                            onAction(HomeActions.OnEditTaskDateChanged(selectedDate))
                            showDatePicker = false
                        },
                        onDismiss = { showDatePicker = false }
                    )
                }
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.priority),
                    style = TudeeTheme.textStyle.title.medium,
                    color = TudeeTheme.color.textColors.title
                )
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(priorities) { (priority, data) ->
                        val (label, iconRes, backgroundColor) = data
                        TudeeChip(
                            label = label,
                            icon = painterResource(iconRes),
                            backgroundColor = if (state.selectedTask.taskPriority == priority) backgroundColor else TudeeTheme.color.surfaceLow,
                            labelColor = if (state.selectedTask.taskPriority == priority) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.body,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clip(CircleShape)
                                .clickWithRipple {
                                    onAction(
                                        HomeActions.OnEditTaskPriorityChanged(
                                            priority
                                        )
                                    )
                                }
                        )
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    text = stringResource(R.string.category),
                    style = TudeeTheme.textStyle.title.medium,
                    color = TudeeTheme.color.textColors.title
                )
            }
            item {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 104.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .heightIn(max = 3000.dp)
                        .fillMaxWidth()
                        .padding(bottom = 160.dp),
                    userScrollEnabled = false
                ) {
                    items(categories) { categories ->

                        CategoryItemWithBadge(
                            categoryPainter = categories.image.toUiImage().asPainter(),
                            showCheckedIcon = state.selectedTask.taskCategory.id == categories.id,
                            badgeBackgroundColor = TudeeTheme.color.statusColors.greenAccent,
                            categoryName = categories.title,
                            categoryImageContentDescription = "Category ${categories.title}",
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickWithRipple {
                                    onAction(
                                        HomeActions.OnEditTaskCategoryChanged(
                                            categories
                                        )
                                    )
                                }
                        )

                    }
                }
            }

        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = TudeeTheme.color.surfaceHigh
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                state = if (state.selectedTask.taskDescription.isNotBlank() == true &&
                    state.selectedTask.taskTitle.isNotBlank() == true &&
                    state.selectedTask.taskCategory.title.isNotBlank() == true

                ) ButtonState.IDLE else ButtonState.DISABLED,
                onClick = {
                    if (mode == TaskContentMode.EDIT) {
                        onAction(HomeActions.OnEditTaskButtonClicked)
                    } else {
                        onAction(HomeActions.OnCreateTaskButtonClicked(state.selectedTask))
                    }
                },
            ) {
                Text(
                    text = stringResource(
                        if (mode == TaskContentMode.EDIT) R.string.save else R.string.add
                    )
                )
            }
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                onClick = { onAction(HomeActions.OnCancelButtonClicked) },
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(
            containerColor = TudeeTheme.color.surface,
            titleContentColor = TudeeTheme.color.textColors.title
        ),
        confirmButton = {},
        dismissButton = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) {
            DatePicker(
                state = datePickerState,
                headline = {},
                colors = DatePickerDefaults.colors(
                    navigationContentColor = TudeeTheme.color.textColors.title,
                    headlineContentColor = TudeeTheme.color.textColors.title,
                    titleContentColor = TudeeTheme.color.textColors.title,
                    weekdayContentColor = TudeeTheme.color.textColors.title,
                    dayContentColor = TudeeTheme.color.textColors.title,
                    containerColor = TudeeTheme.color.statusColors.greenAccent,
                    selectedDayContainerColor = TudeeTheme.color.primary,
                    selectedDayContentColor = TudeeTheme.color.textColors.onPrimary,
                    todayContentColor = TudeeTheme.color.primary,
                    todayDateBorderColor = TudeeTheme.color.primary
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 40.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.clickable {
                        datePickerState.selectedDateMillis = null
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
                            val selectedDateMillis = datePickerState.selectedDateMillis
                            if (selectedDateMillis != null) {
                                val instant = Instant.fromEpochMilliseconds(selectedDateMillis)
                                val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                                val localDate = LocalDate(
                                    year = localDateTime.year,
                                    monthNumber = localDateTime.monthNumber,
                                    dayOfMonth = localDateTime.dayOfMonth
                                )
                                onDateSelected(localDate)
                            }
                        },
                        text = stringResource(R.string.date_picker_ok_button_text),
                        color = TudeeTheme.color.primary,
                        style = TudeeTheme.textStyle.label.large
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskContentPreview() {
    TudeeTheme {
        TaskContent(
            state = HomeUiState(
                selectedTask = TaskUiState(
                    taskId = "1",
                    taskTitle = "Study Kotlin",
                    taskDescription = "Read about coroutines and flows",
                    taskPriority = TaskPriorityUiState.MEDIUM,
                    taskCategory = CategoryUiState(
                        id = 2,
                        title = "Education",
                        image = "education",
                        isPredefined = true
                    ),
                    taskAssignedDate = LocalDate(2025, 6, 25)
                )
            ),
            categories = listOf(
                CategoryUiState(
                    id = 1,
                    title = "Health",
                    image = "health",
                    isPredefined = true
                ),
                CategoryUiState(
                    id = 2,
                    title = "Education",
                    image = "education",
                    isPredefined = true
                ),
                CategoryUiState(
                    id = 3,
                    title = "Work",
                    image = "work",
                    isPredefined = true
                )
            ),
            onAction = {},
            mode = TaskContentMode.ADD
        )
    }
}
