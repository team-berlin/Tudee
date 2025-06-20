package com.example.tudee.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screens.home.components.TaskList
import com.example.tudee.presentation.screens.home.components.TitleSection
import kotlinx.datetime.LocalDate


@Composable
fun TasksSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    statusTitle: String,
    numberOfElement: String,
    tasks: List<Task>
) {
    Column(modifier = modifier) {
        TitleSection(
            modifier = modifier.padding(top=24.dp, bottom = 8.dp),
            statusTitle = statusTitle,
            numberOfElement = numberOfElement,
            onClick = onClick,

        )
        TaskList(
            tasks = tasks,
            onClick = { /* Handle task click */ }
        )
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    TudeeTheme {
        Column(modifier = Modifier.padding(start=16.dp)) {
            TasksSection(
                onClick = { /* Handle click */ },
                statusTitle = "Todo",
                numberOfElement = "3",
                tasks = listOf(
                    Task(
                        id = 1,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    ),
                    Task(
                        id = 2,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    ),
                    Task(
                        id = 3,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    )
                )
            )
            TasksSection(
                onClick = { /* Handle click */ },
                statusTitle = "In Progress",
                numberOfElement = "2",
                tasks = listOf(
                    Task(
                        id = 4,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    ),
                    Task(
                        id = 5,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    ),
                    Task(
                        id = 5,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    ),
                    Task(
                        id = 5,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    )
                )
            )
            TasksSection(
                onClick = { /* Handle click */ },
                statusTitle = "Done",
                numberOfElement = "1",
                tasks = listOf(
                    Task(
                        id =6,
                        title = "Organize Study Desk",
                        description = "Description for task 2",
                        priority = TaskPriority.MEDIUM,
                        categoryId = 2,
                        status = TaskStatus.DONE,
                        assignedDate = LocalDate(2025, 6, 19)
                    )
                )
            )
        }
    }
}