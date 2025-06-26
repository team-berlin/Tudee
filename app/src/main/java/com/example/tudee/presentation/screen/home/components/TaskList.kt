package com.example.tudee.presentation.screen.home.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.CategoryTaskComponent
import com.example.tudee.presentation.screen.home.toTaskPriorityUiState
import com.example.tudee.presentation.screen.home.toTaskStatusUiState
import com.example.tudee.presentation.screen.home.viewmodel.CategoryUiState
import com.example.tudee.presentation.screen.home.viewmodel.HomeActions
import com.example.tudee.presentation.screen.home.viewmodel.TaskPriorityUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskUiState
import com.example.tudee.presentation.utils.toCategoryIcon
import kotlinx.datetime.LocalDate

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<TaskUiState> = emptyList(),
    actions: (HomeActions) -> Unit = {},
) {
    val rowCount = if (tasks.size > 2) 2 else 1
    LazyHorizontalGrid(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(if (rowCount == 2) 230.dp else 111.dp),
        rows = GridCells.Fixed(rowCount),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { task ->
            val priorityIcon = when (task.taskPriority) {
                TaskPriorityUiState.HIGH -> R.drawable.ic_priority_high
                TaskPriorityUiState.MEDIUM -> R.drawable.ic_priority_medium
                TaskPriorityUiState.LOW -> R.drawable.ic_priority_low
            }

            val priorityColor = when (task.taskPriority) {
                TaskPriorityUiState.HIGH -> TudeeTheme.color.statusColors.pinkAccent
                TaskPriorityUiState.MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
                TaskPriorityUiState.LOW -> TudeeTheme.color.statusColors.greenAccent
            }
            val priorityLabel = when (task.taskPriority) {
                TaskPriorityUiState.HIGH -> R.string.high
                TaskPriorityUiState.MEDIUM -> R.string.medium
                TaskPriorityUiState.LOW -> R.string.low
            }

            CategoryTaskComponent(
                modifier = Modifier
                    .width(328.dp),
                title = task.taskTitle,
                description = task.taskDescription,
                priority = stringResource(priorityLabel),
                priorityIcon = painterResource(id = priorityIcon),
                priorityBackgroundColor = priorityColor,
                taskIcon = {
                    Image(
                        painter = painterResource(task.taskCategory.image.toCategoryIcon()),
                        contentDescription = "taskIcon",
                    )
                },
                onClick = { actions(HomeActions.OnTaskCardClicked(task)) }
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun TaskListPreview() {
    TudeeTheme {
        TaskList(
            tasks = listOf(
                TaskUiState(
                    taskId = "1",
                    taskTitle = "Study Biology",
                    taskDescription = "Review chapters 3 and 4 for the exam",
                    taskPriority = TaskPriority.HIGH.toTaskPriorityUiState(),
                    taskCategory = CategoryUiState(
                        id = 1,
                        title = "Education",
                        image = "education", // must match getCategoryIcon key
                        isPredefined = true
                    ),
                    taskStatusUiState = TaskStatus.IN_PROGRESS.toTaskStatusUiState(),
                    taskAssignedDate = LocalDate(2025, 6, 25)
                ),
                TaskUiState(
                    taskId = "2",
                    taskTitle = "Grocery Shopping",
                    taskDescription = "Buy fruits, vegetables, and milk",
                    taskPriority = TaskPriority.MEDIUM.toTaskPriorityUiState(),
                    taskCategory = CategoryUiState(
                        id = 2,
                        title = "Shopping",
                        image = "shopping",
                        isPredefined = true
                    ),
                    taskStatusUiState = TaskStatus.TODO.toTaskStatusUiState(),
                    taskAssignedDate = LocalDate(2025, 6, 25)
                ),
                TaskUiState(
                    taskId = "3",
                    taskTitle = "Evening Walk",
                    taskDescription = "Walk 30 minutes in the park",
                    taskPriority = TaskPriority.LOW.toTaskPriorityUiState(),
                    taskCategory = CategoryUiState(
                        id = 3,
                        title = "Health",
                        image = "health",
                        isPredefined = true
                    ),
                    taskStatusUiState = TaskStatus.DONE.toTaskStatusUiState(),
                    taskAssignedDate = LocalDate(2025, 6, 25)
                )
            ),
            actions = {}
        )
    }
}
