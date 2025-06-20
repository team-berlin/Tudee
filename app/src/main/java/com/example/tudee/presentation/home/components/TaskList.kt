package com.example.tudee.presentation.screens.home.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.CategoryTaskComponent
import kotlinx.datetime.LocalDate

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task> = dummyTasks,
    onClick: (Int) -> Unit = {}
) {
    LazyHorizontalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp),
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks.size) { index ->
            val task = tasks[index]
            val priorityIcon = when (task.priority) {
                TaskPriority.HIGH -> R.drawable.ic_priority_high
                TaskPriority.MEDIUM -> R.drawable.ic_priority_medium
                TaskPriority.LOW -> R.drawable.ic_priority_low
            }

            val priorityColor = when (task.priority) {
                TaskPriority.HIGH -> TudeeTheme.color.statusColors.pinkAccent
                TaskPriority.MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
                TaskPriority.LOW -> TudeeTheme.color.statusColors.greenAccent
            }
            val priorityLabel = when (task.priority) {
                TaskPriority.HIGH -> "High"
                TaskPriority.MEDIUM -> "Medium"
                TaskPriority.LOW -> "Low"
            }

            CategoryTaskComponent(
                modifier = Modifier
                    .width(328.dp),
                title = task.title,
                description = task.description,
                priority = priorityLabel,
                priorityIcon = painterResource(id = priorityIcon),
                priorityBackgroundColor = priorityColor,
                taskIcon = {
                    Image(
                        painter = painterResource(R.drawable.tudee_),
                        contentDescription = "taskIcon",
                    )
                },
                onClick = { onClick(index) }
            )
        }
    }
}

// Dummy data for preview
private val dummyTasks = listOf(
    Task(
        id = 1,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        priority = TaskPriority.HIGH,
        categoryId = 1,
        status = TaskStatus.IN_PROGRESS,
        assignedDate = LocalDate(2025, 6, 18)
    ),
    Task(
        id = 2,
        title = "Organize Study Desk",
        description = "Description for task 2",
        priority = TaskPriority.MEDIUM,
        categoryId = 2,
        status = TaskStatus.DONE,
        assignedDate = LocalDate(2025, 6, 19)
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun TaskListPreview() {
    TudeeTheme {
        TaskList()
    }

}