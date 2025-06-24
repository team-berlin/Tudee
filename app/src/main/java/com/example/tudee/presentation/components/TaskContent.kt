package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.PrimaryButton
import com.example.tudee.presentation.components.buttons.SecondaryButton
import com.example.tudee.presentation.screen.home.viewmodel.CategoryUiState
import com.example.tudee.presentation.screen.home.viewmodel.HomeActions
import com.example.tudee.presentation.screen.home.viewmodel.HomeUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskPriorityUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskUiState

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                modifier = Modifier.clickable { showDatePicker = true },
                                painter = painterResource(R.drawable.ic_calendar),
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
                            backgroundColor = if (state.selectedTask?.taskPriority == priority) backgroundColor else TudeeTheme.color.surfaceLow,
                            labelColor = if (state.selectedTask?.taskPriority == priority) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.body,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clickable { onAction(HomeActions.OnEditTaskPriorityChanged(priority)) }
                        )
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
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
                    modifier =Modifier
                        .heightIn(max = 3000.dp)
                        .fillMaxWidth()
                        .padding(bottom = 160.dp),
                    userScrollEnabled = false
                ) {
                    items(categories) { categories ->
                        CategoryItemWithBadge(
                            categoryPainter = painterResource(
                                id = categories.image
                                    ?: R.drawable.category
                            ),
                            showCheckedIcon = state.selectedTask?.taskCategory?.id == categories.id,
                            badgeBackgroundColor = TudeeTheme.color.statusColors.greenAccent,
                            categoryName = categories.title,
                            categoryImageContentDescription = "Category ${categories.title}",
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clickable {
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
                state = if (state.selectedTask?.taskDescription?.isNotBlank() == true && state.selectedTask?.taskTitle?.isNotBlank() == true) ButtonState.IDLE else ButtonState.DISABLED,
                onClick = { 
                    if (mode == TaskContentMode.EDIT) {
                        onAction(HomeActions.OnEditTaskButtonClicked)
                    } else {
                        onAction(HomeActions.OnCreateTaskButtonClicked(state.selectedTask!!))
                    }
                },
            ) {
                Text(text = stringResource(
                    if (mode == TaskContentMode.EDIT) R.string.edit else R.string.add
                ))
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
        confirmButton = {
            Button(onClick = {
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
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun TaskContentPreview() {
    TudeeTheme {
        val sampleCategories = listOf(
            CategoryUiState(
                id = "1",
                title = "Education",
                image = R.drawable.ic_education
            ),
            CategoryUiState(
                id = "2",
                title = "Work",
                image = R.drawable.ic_education
            ),
            CategoryUiState(
                id = "3",
                title = "Personal",
                image = R.drawable.ic_education
            ),
            CategoryUiState(
                id = "4",
                title = "Education",
                image = R.drawable.ic_education
            ),
            CategoryUiState(
                id = "5",
                title = "Work",
                image = R.drawable.ic_education
            ),
            CategoryUiState(
                id = "6",
                title = "Personal",
                image = R.drawable.ic_education
            ),

            CategoryUiState(
                id = "4",
                title = "Education",
                image = R.drawable.ic_education
            ),
            CategoryUiState(
                id = "5",
                title = "Work",
                image = R.drawable.ic_education
            ),
            CategoryUiState(
                id = "6",
                title = "Personal",
                image = R.drawable.ic_education
            )
        )


        var homeUiState by remember {
            mutableStateOf(
                HomeUiState(
                    taskUiState = TaskUiState(),
                )
            )
        }

        TaskContent(
            state = homeUiState,
            categories = sampleCategories,
            onAction = { action ->
                when (action) {
                    is HomeActions.OnEditTaskTitleChanged -> {
                        homeUiState = homeUiState.copy(
                            taskUiState = homeUiState.taskUiState.copy(taskTitle = action.title),
                            selectedTask = homeUiState.selectedTask.copy(taskTitle = action.title)
                        )
                    }

                    is HomeActions.OnEditTaskDescriptionChanged -> {
                        homeUiState = homeUiState.copy(
                            taskUiState = homeUiState.taskUiState.copy(taskDescription = action.description),
                            selectedTask = homeUiState.selectedTask.copy(taskDescription = action.description)
                        )
                    }

                    is HomeActions.OnEditTaskPriorityChanged -> {
                        homeUiState = homeUiState.copy(
                            taskUiState = homeUiState.taskUiState.copy(taskPriority = action.priority),
                            selectedTask = homeUiState.selectedTask.copy(taskPriority = action.priority)
                        )
                    }

                    is HomeActions.OnEditTaskCategoryChanged -> {
                        homeUiState = homeUiState.copy(
                            taskUiState = homeUiState.taskUiState.copy(taskCategory = action.category),
                            selectedTask = homeUiState.selectedTask.copy(taskCategory = action.category)
                        )
                    }

                    is HomeActions.OnCreateTaskButtonClicked -> {
                    }

                    is HomeActions.OnCancelButtonClicked -> {
                        homeUiState = homeUiState.copy(
                            taskUiState = TaskUiState(),
                        )
                    }

                    else -> {
                    }
                }
            }
        )
    }
}
