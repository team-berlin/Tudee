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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.composables.buttons.PrimaryButton
import com.example.tudee.presentation.composables.buttons.SecondaryButton
import com.example.tudee.ui.home.viewmodel.CategoryUiState
import com.example.tudee.ui.home.viewmodel.HomeUiState
import com.example.tudee.ui.home.viewmodel.TaskPriorityUiState
import com.example.tudee.ui.home.viewmodel.TaskUiState
import kotlinx.datetime.LocalDate

@Composable
fun AddTaskContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    categories: List<CategoryUiState>,
) {
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
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Text(
                text = stringResource(R.string.add_task_title),
                style = TudeeTheme.textStyle.title.large,
                color = TudeeTheme.color.textColors.title
            )

            TudeeTextField(
                modifier = Modifier.padding(top = 12.dp),
                value = state.taskUiState.taskTitle,
                onValueChange = { },
                placeholder = stringResource(R.string.task_title),
                leadingContent = { isFocused ->
                    DefaultLeadingContent(
                        painter = painterResource(R.drawable.ic_notebook),
                        isFocused = isFocused
                    )
                }
            )


            TudeeTextField(

                value = state.taskUiState.taskDescription,
                onValueChange = { },
                placeholder = stringResource(R.string.description),
                singleLine = false,
                modifier = Modifier
                    .height(168.dp)
                    .padding(top = 16.dp)
            )

            TudeeTextField(
                modifier = Modifier.padding(top = 16.dp),
                value = state.taskUiState.taskAssignedDate.toString(),
                onValueChange = { },
                placeholder = stringResource(R.string.set_due_date),
                leadingContent = { isFocused ->
                    DefaultLeadingContent(
                        painter = painterResource(R.drawable.ic_calendar),
                        isFocused = isFocused
                    )
                }
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.priority),
                style = TudeeTheme.textStyle.title.medium,
                color = TudeeTheme.color.textColors.title
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(priorities) { (priority, data) ->
                    val (label, iconRes, backgroundColor) = data
                    TudeeChip(
                        label = label,
                        icon = painterResource(iconRes),
                        backgroundColor = if (state.taskUiState.taskPriority == priority) backgroundColor else TudeeTheme.color.surfaceLow,
                        labelColor = if (state.taskUiState.taskPriority == priority) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.body,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable { }
                    )
                }
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.category),
                style = TudeeTheme.textStyle.title.medium,
                color = TudeeTheme.color.textColors.title
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(categories) { categories ->
                    CategoryItemWithBadge(
                        categoryPainter = painterResource(
                            id = categories.image
                                ?: R.drawable.category
                        ),
                        categoryName = categories.title,
                        categoryImageContentDescription = "Education Category",
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clickable { }
                    )
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
                state = ButtonState.DISABLED,
                onClick = { /* TODO: Handle add task */ },
            ) {
                Text(text = stringResource(R.string.add))
            }
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                onClick = { /* TODO: Handle cancel */ },
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun AddTaskContentPreview() {
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
            )
        )

        AddTaskContent(
            state = HomeUiState(
                taskUiState = TaskUiState(
                    taskTitle = "Sample Task",
                    taskDescription = "This is a sample description",
                    taskAssignedDate = LocalDate.parse("2025-06-20"),
                    taskPriority = TaskPriorityUiState.MEDIUM,
                    taskCategory = sampleCategories.first()
                )
            ),
            categories = sampleCategories
        )
    }
}