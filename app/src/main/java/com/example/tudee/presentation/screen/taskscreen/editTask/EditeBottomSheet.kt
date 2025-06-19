package com.example.tudee.presentation.screen.taskscreen.editTask

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tudee.presentation.viewmodel.AddTaskBottomSheetViewModel
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.screen.TaskContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditeBottomSheet(taskId: Long = 1L) {
    val viewModel: AddTaskBottomSheetViewModel = koinViewModel()
    val taskScreenUiState by viewModel.uiState.collectAsState()
    val addButtonState by viewModel.isTaskValid.collectAsState()

    Box(modifier = Modifier.padding(10.dp).size(width = 200.dp, height = 200.dp)) {
        Button(
            onClick = {
                Log.d("EditScreen", "Edit button clicked")
                viewModel.run {
                    showButtonSheet()
                    getTaskInfoById(taskId)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        ) {
            Text("Edit Task", color = TudeeTheme.color.textColors.onPrimary)
        }


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
            onCancelButtonClicked = viewModel::onCancelClicked,
        )
    }
}