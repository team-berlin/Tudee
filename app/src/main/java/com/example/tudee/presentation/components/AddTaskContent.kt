package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.example.tudee.presentation.utils.toCategoryIcon

@Composable
fun AddTaskContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    categories: List<CategoryUiState>,
    onAction: (HomeActions) -> Unit,
) {
    val addButtonClick by remember { mutableStateOf(false) }
    val priorities = listOf(
        TaskPriorityUiState.HIGH to Triple(
            "High",
            R.drawable.ic_priority_high,
            TudeeTheme.color.statusColors.pinkAccent
        ),
        TaskPriorityUiState.MEDIUM to Triple(
            "Medium",
            R.drawable.ic_priority_medium,
            TudeeTheme.color.statusColors.yellowAccent
        ),
        TaskPriorityUiState.LOW to Triple(
            "Low",
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
                    text = stringResource(R.string.add_task_title),
                    style = TudeeTheme.textStyle.title.large,
                    color = TudeeTheme.color.textColors.title
                )
            }
            item {
                TudeeTextField(
                    modifier = Modifier.padding(top = 12.dp),
                    value = state.taskUiState.taskTitle,
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
                    value = state.taskUiState.taskDescription,
                    onValueChange = { onAction(HomeActions.OnEditTaskDescriptionChanged(it)) },
                    placeholder = stringResource(R.string.description),
                    singleLine = false,
                    modifier = Modifier
                        .height(168.dp)
                        .padding(top = 16.dp)
                )

            }
            item {
                TudeeTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = state.taskUiState.taskAssignedDate.toString(),
                    onValueChange = { },
                    placeholder = stringResource(R.string.set_due_date),
                    leadingContent = { isFocused ->
                        DefaultLeadingContent(
                            painter = painterResource(R.drawable.ic_add_calendar),
                            isFocused = isFocused
                        )
                    }
                )
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
                        val isSelected: Boolean = state.taskUiState.taskPriority == priority

                        val (label, iconRes, backgroundColor) = data
                        TudeeChip(
                            label = label,
                            icon = painterResource(iconRes),
                            backgroundColor = if (isSelected) backgroundColor else TudeeTheme.color.surfaceLow,
                            labelColor = if (isSelected) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.body,
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
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .padding(bottom = 148.dp),
                ) {
                    items(categories) { categories ->
                        CategoryItemWithBadge(
                            categoryPainter = painterResource(
                                categories.image.toCategoryIcon()
                            ),
                            showCheckedIcon = state.taskUiState.taskCategory?.id == categories.id,
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
                state = if (addButtonClick) ButtonState.IDLE else ButtonState.DISABLED,
                onClick = { onAction(HomeActions.OnCreateTaskButtonClicked(state.taskUiState)) },
            ) {
                Text(text = stringResource(R.string.add))
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

@Preview(showBackground = true)
@Composable
private fun AddTaskContentPreview() {
    TudeeTheme {
        val sampleCategories = listOf(
            CategoryUiState(
                id = 1,
                title = "Education",
                image = "education"
            ),
            CategoryUiState(
                id = 2,
                title = "Work",
                image = "work"
            ),
            CategoryUiState(
                id = 3,
                title = "Personal",
                image = "personal"
            )
        )

        val taskUiState = TaskUiState(
            taskTitle = "Finish Homework",
            taskDescription = "Complete math and science exercises.",
            taskAssignedDate = kotlinx.datetime.LocalDate(2025, 6, 25),
            taskPriority = TaskPriorityUiState.HIGH,
            taskCategory = sampleCategories.first()
        )

        AddTaskContent(
            state = HomeUiState(taskUiState = taskUiState),
            categories = sampleCategories,
            onAction = {}
        )
    }
}
