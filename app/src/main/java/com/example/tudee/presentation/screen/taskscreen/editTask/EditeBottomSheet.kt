//package com.example.tudee.presentation.screen.taskscreen.editTask
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import com.example.tudee.presentation.viewmodel.AddTaskBottomSheetViewModel
//import com.example.tudee.presentation.screen.TaskContent
//import org.koin.androidx.compose.koinViewModel
//
//@Composable
//fun EditeBottomSheet(taskId: Long = 1L) {
//    val viewModel: AddTaskBottomSheetViewModel = koinViewModel()
//
//    val taskScreenUiState by viewModel.uiState.collectAsState()
//    val addButtonState by viewModel.isTaskValid.collectAsState()
//
//    viewModel.toggleEditMode(true)
//    viewModel.getTaskInfoById(taskId)
//
//        TaskContent(
//            taskState = taskScreenUiState,
//            onTaskTitleChanged = viewModel::onUpdateTaskTitle,
//            onTaskDescriptionChanged = viewModel::onUpdateTaskDescription,
//            onUpdateTaskDueDate = viewModel::onUpdateTaskDueDate,
//            onUpdateTaskPriority = viewModel::onSelectTaskPriority,
//            onSelectTaskCategory = viewModel::onSelectTaskCategory,
//            addButtonState = addButtonState,
//            hideButtonSheet = viewModel::hideButtonSheet,
//            isEditMode = true,
//            onSaveClicked = viewModel::onSaveClicked,
//            onAddClicked = viewModel::onAddNewTaskClicked,
//            onCancelButtonClicked = viewModel::onCancelClicked,
//            onDateFieldClicked = viewModel::onDateFieldClicked,
//            onEditClicked = viewModel::getTaskInfoById,
//            onDismissDatePicker = viewModel::onDismissDatePicker,
//            onConfirmDatePicker = viewModel::onConfirmDatePicker,
//        )
//    }
