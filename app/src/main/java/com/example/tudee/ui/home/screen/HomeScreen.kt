package com.example.tudee.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.home.components.TasksSection
import com.example.tudee.ui.home.components.HomeOverviewCard
import kotlinx.datetime.LocalDate

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = TudeeTheme.color.surface)
    ) {
        BackgroundBlueCard()
        HomeOverView()
    }
}

@Composable
fun BackgroundBlueCard(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .height(176.dp)
            .background(color = TudeeTheme.color.primary)
    )
}

@Composable
fun HomeOverView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(top = 72.dp)
            .padding(
                horizontal = 16.dp
            )
            .clip(
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
            )
            .background(color = TudeeTheme.color.surface)

    ) {
        HomeOverviewCard(
            todayDate = "22",
            month = "Jun",
            year = "2025",
            tasksDoneCount = "2",
            tasksTodoCount = "2",
            tasksInProgressCount = "2"
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            TasksSection(
                onClick = { /* Handle click */ },
                statusTitle = stringResource(R.string.todo),
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
                statusTitle = stringResource(R.string.in_progress),
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
                statusTitle = stringResource(R.string.done),
                numberOfElement = "1",
                tasks = listOf(
                    Task(
                        id = 6,
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
@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}