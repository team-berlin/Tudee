package com.example.tudee.presentation.screen.home.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.screen.home.viewmodel.HomeActions
import com.example.tudee.presentation.screen.home.viewmodel.TaskUiState


@Composable
fun TasksSection(
    modifier: Modifier = Modifier,
    actions: (HomeActions) -> Unit = {},
    statusTitle: String,
    numberOfElement: String,
    tasks: List<TaskUiState>
) {
    Log.d("TasksSection", "Tasks: $tasks")
    Column(modifier = modifier) {
        TitleSection(
            modifier = modifier.padding(top = 24.dp, bottom = 8.dp),
            statusTitle = statusTitle,
            numberOfElement = numberOfElement,
            onClick = { actions(HomeActions.OnArrowClicked(tasks.firstOrNull()?.taskStatusUiState)) },
        )
        TaskList(
            tasks = tasks,
            actions = actions
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    TudeeTheme {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            TasksSection(
                actions = {},
                statusTitle = "Todo",
                numberOfElement = "3",
                tasks = emptyList()
            )
            TasksSection(
                actions = {},
                statusTitle = "In Progress",
                numberOfElement = "2",
                tasks = emptyList()
            )
            TasksSection(
                actions = {},
                statusTitle = "Done",
                numberOfElement = "1",
                tasks = emptyList()
            )
        }
    }
}