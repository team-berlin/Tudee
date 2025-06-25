package com.example.tudee.presentation.screen.task_screen.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.TabBarComponent
import com.example.tudee.presentation.components.TudeeDateDialog
import com.example.tudee.presentation.components.TudeeScaffold
import com.example.tudee.presentation.screen.task_screen.component.DateSection
import com.example.tudee.presentation.screen.task_screen.component.DeleteConfirmationBottomSheet
import com.example.tudee.presentation.screen.task_screen.component.SnackBarSection
import com.example.tudee.presentation.screen.task_screen.component.TaskContent
import com.example.tudee.presentation.screen.task_screen.component.TaskScreenBottomAppBar
import com.example.tudee.presentation.screen.task_screen.component.TaskScreenFloatingActionButton
import com.example.tudee.presentation.screen.task_screen.component.TaskScreenTopAppBar
import com.example.tudee.presentation.screen.task_screen.component.TasksListContent
import com.example.tudee.presentation.screen.task_screen.interactors.TaskScreenInteractor
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskBottomSheetState
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TasksScreenUiState
import com.example.tudee.presentation.screen.task_screen.viewmodel.TasksScreenViewModel
import com.example.tudee.presentation.viewmodel.TaskBottomSheetViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasksScreen(navController: NavController, tasksScreenViewModel: TasksScreenViewModel) {
    val taskScreenUiState by tasksScreenViewModel.taskScreenUiState.collectAsState()

    val taskBottomSheetViewModel: TaskBottomSheetViewModel = koinViewModel()
    val taskBottomSheetUiState by taskBottomSheetViewModel.uiState.collectAsState()
    val isEditeMode by taskBottomSheetViewModel.isTaskValid.collectAsState()

    TasksScreenContent(
        navController = navController,
        taskBottomSheetUiState = taskBottomSheetUiState,
        taskBottomSheetViewModel = taskBottomSheetViewModel,
        taskScreenUiState = taskScreenUiState,
        onTabSelected = tasksScreenViewModel::onTabSelected,
        onTaskCardClicked = tasksScreenViewModel::onTaskCardClicked,
        onDayCardClicked = tasksScreenViewModel::onDayCardClicked,
        onCalendarClicked = tasksScreenViewModel::onCalendarClicked,
        onPreviousArrowClicked = tasksScreenViewModel::onPreviousArrowClicked,
        onNextArrowClicked = tasksScreenViewModel::onNextArrowClicked,
        onDeleteIconClicked = tasksScreenViewModel::onDeleteIconClicked,
        onDeleteButtonClicked = tasksScreenViewModel::onConfirmDelete,
        onBottomSheetDismissed = tasksScreenViewModel::onBottomSheetDismissed,
        onCancelButtonClicked = tasksScreenViewModel::onCancelButtonClicked,
        onDismissDatePicker = tasksScreenViewModel::onDismissDatePicker,
        onConfirmDatePicker = tasksScreenViewModel::onConfirmDatePicker,
        hideSnackBar = tasksScreenViewModel::hideSnackBar,
        version = tasksScreenViewModel.triggerEffectVersion.collectAsState().value,
        hideDetailsBottomSheet = tasksScreenViewModel::hideDetailsBottomSheet,
        Interactor = tasksScreenViewModel,
        isEditeMode = isEditeMode,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenContent(
    navController: NavController,
    Interactor: TaskScreenInteractor,
    taskScreenUiState: TasksScreenUiState,
    onDayCardClicked: (Int) -> Unit,
    onCalendarClicked: () -> Unit,
    onPreviousArrowClicked: () -> Unit,
    onNextArrowClicked: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onTaskCardClicked: (TaskUiState) -> Unit,
    onDeleteIconClicked: (Long) -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onBottomSheetDismissed: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onConfirmDatePicker: (Long?) -> Unit,
    onDismissDatePicker: () -> Unit,
    hideSnackBar: () -> Unit,
    taskBottomSheetUiState: TaskBottomSheetState,
    version: Int,
    hideDetailsBottomSheet: () -> Unit,
    taskBottomSheetViewModel: TaskBottomSheetViewModel,
    isEditeMode:Boolean

) {

    TudeeScaffold(
        showTopAppBar = true,
        topAppBar = { TaskScreenTopAppBar() },
        showBottomBar = true,
        bottomBarContent = { TaskScreenBottomAppBar(navController) },
        showFab = true,
        floatingActionButton = {
            TaskScreenFloatingActionButton {
                taskBottomSheetViewModel.showButtonSheet()
            }
        }) { paddingValues ->

        Box(
            Modifier
                .fillMaxSize()
                .background(TudeeTheme.color.surfaceHigh)
                .padding(paddingValues)

        ) {

            if (taskScreenUiState.taskDetailsUiState != null) {
                ModalBottomSheet(
                    onDismissRequest = {
                        hideDetailsBottomSheet()
                    },
                    sheetState = rememberModalBottomSheetState(),
                    containerColor = TudeeTheme.color.surface
                ) {
                    TaskDetailsScreen(
                        taskDetailsState =
                            taskScreenUiState.taskDetailsUiState!!,
                        taskBottomSheetViewModel,
                         hideDetailsBottomSheet
                    )
                }
            }

        }


        TaskContent(
            taskState = taskBottomSheetUiState,
            onTaskTitleChanged = taskBottomSheetViewModel::onUpdateTaskTitle,
            onTaskDescriptionChanged = taskBottomSheetViewModel::onUpdateTaskDescription,
            onUpdateTaskDueDate = taskBottomSheetViewModel::onUpdateTaskDueDate,
            onUpdateTaskPriority = taskBottomSheetViewModel::onSelectTaskPriority,
            onSelectTaskCategory = taskBottomSheetViewModel::onSelectTaskCategory,
            hideButtonSheet = taskBottomSheetViewModel::hideButtonSheet,
            isEditMode =isEditeMode,
            onSaveClicked = taskBottomSheetViewModel::onSaveClicked,
            onAddClicked = taskBottomSheetViewModel::onAddNewTaskClicked,
            onCancelButtonClicked = taskBottomSheetViewModel::onCancelClicked,
            onDateFieldClicked = taskBottomSheetViewModel::onDateFieldClicked,
            onConfirmDatePicker = taskBottomSheetViewModel::onConfirmDatePicker,
            onDismissDatePicker =taskBottomSheetViewModel::onDismissDatePicker
        )




        if (taskScreenUiState.dateUiState.isDatePickerVisible) {
            TudeeDateDialog(
                onDismiss = onDismissDatePicker, onConfirm = onConfirmDatePicker, onClear = {})
        }

        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            DateSection(
                datePickerUiState = taskScreenUiState.dateUiState,
                listOfDateCardUiState = taskScreenUiState.dateUiState.daysCardsData,
                onCalendarClicked = onCalendarClicked,
                onPreviousArrowClicked = onPreviousArrowClicked,
                onNextArrowClicked = onNextArrowClicked,
                onDayCardClicked = onDayCardClicked,
                version = version
            )

            TabBarComponent(
                modifier = Modifier.padding(top = 12.dp),
                selectedTabIndex = taskScreenUiState.selectedTabIndex,
                tabBarItems = taskScreenUiState.listOfTabBarItem,
                onTabSelected = onTabSelected
            )

            TasksListContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surface)
                    .padding(top = 12.dp)
                    .weight(1f),
                listOfTasks = taskScreenUiState.listOfTasksUiState,
                onTaskCardClicked = { onTaskCardClicked(it) },
                onDeleteIconClick = onDeleteIconClicked,
            )

            DeleteConfirmationBottomSheet(
                isBottomSheetVisible = taskScreenUiState.isBottomSheetVisible,
                title = stringResource(R.string.delete_task),
                subtitle = stringResource(R.string.delete_task_check),
                onBottomSheetDismissed = onBottomSheetDismissed,
                onDeleteButtonClicked = onDeleteButtonClicked,
                onCancelButtonClicked = onCancelButtonClicked,
                deleteButtonUiState = taskScreenUiState.deleteBottomSheetUiState.deleteButtonState,
                cancelButtonUiState = taskScreenUiState.deleteBottomSheetUiState.cancelButtonState
            )

        }

    }
    SnackBarSection(
        isSnackBarVisible = taskScreenUiState.isSnackBarVisible, hideSnackBar = hideSnackBar
    )
}