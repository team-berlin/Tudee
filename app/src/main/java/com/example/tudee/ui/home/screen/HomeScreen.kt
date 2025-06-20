package com.example.tudee.ui.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.AppBar
import com.example.tudee.presentation.components.TudeeScaffold
import com.example.tudee.presentation.composables.buttons.FabButton
import com.example.tudee.presentation.home.components.TasksSection
import com.example.tudee.presentation.screens.home.components.NoTask
import com.example.tudee.ui.home.components.HomeOverviewCard
import com.example.tudee.ui.home.viewmodel.HomeActions
import com.example.tudee.ui.home.viewmodel.HomeUiState
import com.example.tudee.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navigateDoneTasks: () -> Unit = {},
    navigateInProgressTasks: () -> Unit = {},
    navigateTodoTasks: () -> Unit = {}
) {
    val state by viewModel.homeUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.init()
    }
    LaunchedEffect(
        state.navigateDoneTasks,
        state.navigateInProgressTasks,
        state.navigateTodoTasks
    ) {
        if (state.navigateDoneTasks) {
            navigateDoneTasks()
        } else if (state.navigateInProgressTasks) {
            navigateInProgressTasks()
        } else if (state.navigateTodoTasks) {
            navigateTodoTasks()
        } else {
            viewModel.resetStatus()
        }
    }
    TudeeTheme(isDarkTheme = state.isDarkMode) {
        HomeContent(
            modifier = modifier,
            state = state,
            actions = viewModel::handleActions,
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    actions: (HomeActions) -> Unit = {}
) {

    TudeeScaffold(
        showTopAppBar = true,
        topAppBar = {
            AppBar(
                isDarkMode = state.isDarkMode,
                onThemeChanged = {
                    actions(
                        HomeActions.OnThemeChanged(it)
                    )
                }
            )
        },
        showFab = true,
        floatingActionButton = {
            FabButton(
                onClick = {
                    actions(
                        HomeActions.OnFabClicked
                    )
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.note_add),
                    contentDescription = null
                )
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .background(color = TudeeTheme.color.surface)

        ) {
            BackgroundBlueCard()
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
//                    .padding(top = 72.dp)
                    .padding(
                        horizontal = 16.dp
                    )
                    .clip(
                        RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                    )
                    .background(color = TudeeTheme.color.surface)

            ) {
                HomeOverviewCard(
                    todayDate = state.taskTodayDateUiState.todayDateNumber,
                    month = state.taskTodayDateUiState.month,
                    year = state.taskTodayDateUiState.year,
                    tasksDoneCount = state.todayTasksDoneCount,
                    tasksTodoCount = state.todayTasksTodoCount,
                    tasksInProgressCount = state.todayTasksInProgressCount,
                    sliderUiState = state.sliderUiState
                )
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        if (state.todayTasksTodoCount.isEmpty() &&
                            state.todayTasksInProgressCount.isEmpty() &&
                            state.todayTasksTodoCount.isEmpty()) {
                            NoTask()
                        } else {
                            TasksSection(
                                actions = actions,
                                statusTitle = stringResource(R.string.todo),
                                numberOfElement = state.todayTasksTodoCount,
                                tasks = state.todayTasksTodo
                            )
                            TasksSection(
                                actions = actions,
                                statusTitle = stringResource(R.string.in_progress),
                                numberOfElement = state.todayTasksInProgressCount,
                                tasks = state.todayTasksInProgress
                            )
                            TasksSection(
                                actions = actions,
                                statusTitle = stringResource(R.string.done),
                                numberOfElement = state.todayTasksDoneCount,
                                tasks = state.todayTasksDone
                            )
                        }
                    }
            }
        }
        AnimatedVisibility(state.isBottomSheetVisible) {

        }
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TudeeTheme {
        HomeContent(

            state = HomeUiState(),
            actions = {}
        )
    }
}
