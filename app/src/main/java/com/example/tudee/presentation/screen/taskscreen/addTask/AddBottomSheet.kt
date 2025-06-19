package com.example.tudee.presentation.screen.taskscreen.addTask

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tudee.presentation.viewmodel.TaskViewModel
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.screen.TaskContent
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun AddBottomSheet() {
    val viewModel: TaskViewModel = koinViewModel()
    val taskScreenUiState by viewModel.uiState.collectAsState()
    val addButtonState by viewModel.isTaskValid.collectAsState()


        Button(
            onClick = {
                Log.d("AddScreen", "Add button clicked")
                viewModel.showButtonSheet()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
        ) {
            Text("Add Task", color = TudeeTheme.color.textColors.onPrimary)
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
            isEditMode = false,
            onSaveClicked = viewModel::onSaveClicked,
            onAddClicked = viewModel::onAddNewTaskClicked,
            onCancelButtonClicked = viewModel::onCancelClicked,
        )

}