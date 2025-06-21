package com.example.tudee.presentation.screen.task_screen.addTask

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.tudee.presentation.screen.task_screen.ui.TaskContent
import com.example.tudee.presentation.screen.task_screen.viewmodel.AddTaskBottomSheetViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun AddBottomSheet() {
    val viewModel: AddTaskBottomSheetViewModel = koinViewModel()
    val taskScreenUiState by viewModel.uiState.collectAsState()
    val addButtonState by viewModel.isTaskValid.collectAsState()

    viewModel.toggleEditMode(false)

    TaskContent(
        taskState = taskScreenUiState,
        onTaskTitleChanged = viewModel::onUpdateTaskTitle,
        onTaskDescriptionChanged = viewModel::onUpdateTaskDescription,
        onUpdateTaskDueDate = viewModel::onUpdateTaskDueDate,
        onUpdateTaskPriority = viewModel::onSelectTaskPriority,
        onSelectTaskCategory = viewModel::onSelectTaskCategory,
        addButtonState = addButtonState,
        hideButtonSheet = viewModel::hideButtonSheet,
        isEditMode = false,
        onDismissDatePicker = viewModel::onDismissDatePicker,
        onDateFieldClicked = viewModel::onDateFieldClicked,
        onEditClicked = viewModel::getTaskInfoById,
        onSaveClicked = viewModel::onSaveClicked,
        onAddClicked = viewModel::onAddNewTaskClicked,
        onCancelButtonClicked = viewModel::onCancelClicked,
        onConfirmDatePicker = viewModel::onConfirmDatePicker,
    )
}