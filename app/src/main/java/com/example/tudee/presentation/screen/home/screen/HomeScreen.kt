package com.example.tudee.presentation.screen.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.AppBar
import com.example.tudee.presentation.components.TaskContent
import com.example.tudee.presentation.components.TaskContentMode
import com.example.tudee.presentation.components.TudeeScaffold
import com.example.tudee.presentation.components.TudeeTaskDetailsBottomSheet
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.FabButton
import com.example.tudee.presentation.screen.home.components.HomeOverviewCard
import com.example.tudee.presentation.screen.home.components.TasksSection
import com.example.tudee.presentation.screen.home.viewmodel.HomeActions
import com.example.tudee.presentation.screen.home.viewmodel.HomeUiState
import com.example.tudee.presentation.screen.home.viewmodel.HomeViewModel
import com.example.tudee.presentation.screen.home.viewmodel.TaskStatusUiState
import com.example.tudee.presentation.screen.task_screen.component.TaskScreenBottomAppBar
import com.example.tudee.presentation.screen.task_screen.ui.NotTaskForTodayDialogue
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
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
            navController = navController,
            modifier = modifier,
            state = state,
            actions = viewModel::handleActions,
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: HomeUiState,
    actions: (HomeActions) -> Unit = {},
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
        bottomBarContent = { TaskScreenBottomAppBar(navController = navController) },
        showBottomBar = true,
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
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
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
                        tasksDoneCount = state.tasksUiCount.tasksDoneCount,
                        tasksTodoCount = state.tasksUiCount.tasksTodoCount,
                        tasksInProgressCount = state.tasksUiCount.tasksInProgressCount,
                        sliderUiState = state.sliderUiState
                    )
                }
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (state.allTasks.isEmpty()) {
                        NotTaskForTodayDialogue(
                            modifier = Modifier.padding(top = 24.dp),

                             )
                    } else {
                        if (state.todayTasksTodo.isNotEmpty())
                            TasksSection(
                                actions = actions,
                                statusTitle = stringResource(R.string.todo),
                                numberOfElement = state.todayTasksTodo.size.toString(),
                                tasks = state.todayTasksTodo
                            )
                        if (state.todayTasksInProgress.isNotEmpty())
                            TasksSection(
                                actions = actions,
                                statusTitle = stringResource(R.string.in_progress),
                                numberOfElement = state.todayTasksInProgress.size.toString(),
                                tasks = state.todayTasksInProgress
                            )
                        if (state.todayTasksDone.isNotEmpty())
                            TasksSection(
                                actions = actions,
                                statusTitle = stringResource(R.string.done),
                                numberOfElement = state.todayTasksDone.size.toString(),
                                tasks = state.todayTasksDone
                            )
                    }
                }
            }
        }
    }
    if (state.isPreviewSheetVisible) {
        TudeeTaskDetailsBottomSheet(
            isVisible = true,
            task = state.selectedTask,
            onDismissRequest = { actions(HomeActions.OnBottomSheetDismissed) },
            onEditButtonClicked = { actions(HomeActions.OnOpenBottomSheet) },
            isChangeStatusButtonEnable = state.selectedTask.taskStatusUiState != TaskStatusUiState.DONE,
            onMoveActionClicked = {
                actions(
                    HomeActions.OnTaskStatusChanged(
                        if (state.selectedTask.taskStatusUiState == TaskStatusUiState.TODO) TaskStatusUiState.IN_PROGRESS else TaskStatusUiState.DONE
                    )
                )
            },
            changeStatusButtonState = ButtonState.IDLE
        )
    }
    if (state.isBottomSheetVisible) {
        BottomSheetContent(
            state = state,
            onDismiss = { actions(HomeActions.OnBottomSheetDismissed) },
            actions = actions
        )
    }
}


@Composable
fun BackgroundBlueCard(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(color = TudeeTheme.color.primary)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    state: HomeUiState,
    onDismiss: () -> Unit,
    actions: (HomeActions) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TudeeTheme.color.surface
    ) {
        TaskContent(
            state = state,
            categories = state.categories,
            onAction = actions,
            mode = if (state.selectedTask.taskId.isNotEmpty()) TaskContentMode.EDIT else TaskContentMode.ADD
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TudeeTheme {
        HomeContent(
            navController = rememberNavController(),
            state = HomeUiState(),
            actions = {}
        )
    }
}
