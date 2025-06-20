package com.example.tudee.presentation.screen.taskscreen.editTask

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.tudee.presentation.screen.TaskContent
import com.example.tudee.presentation.viewmodel.TaskViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditeBottomSheet(taskId: Long = 1L) {
    val viewModel: TaskViewModel = koinViewModel()
    val taskScreenUiState by viewModel.uiState.collectAsState()
    val addButtonState by viewModel.isTaskValid.collectAsState()

    TaskContent(
        taskState = taskScreenUiState,
        onTaskTitleChanged = viewModel::onUpdateTaskTitle,
        onTaskDescriptionChanged = viewModel::onUpdateTaskDescription,
        onUpdateTaskDueDate = viewModel::onUpdateTaskDueDate,
        onUpdateTaskPriority = viewModel::onSelectTaskPriority,
        onSelectTaskCategory = viewModel::onSelectTaskCategory,
        addButtonState = addButtonState,
        hideButtonSheet = viewModel::hideButtonSheet,
        isEditMode = true,
        onSaveClicked = viewModel::onSaveClicked,
        onAddClicked = viewModel::onAddNewTaskClicked,
        onCancelButtonClicked = viewModel::onCancelClicked
    )
}