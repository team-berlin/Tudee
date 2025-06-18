package com.example.tudee

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddScreen() {
    val viewModel: TaskViewModel = koinViewModel()
    val taskScreenUiState by viewModel.uiState.collectAsState()
    val addButtonState by viewModel.isTaskValid.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(50.dp)) {
        Button(
            onClick = {
                Log.d("AddScreen", "Add button clicked")
                viewModel.showButtonSheet()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        ) {
            Text("Add Task", color = TudeeTheme.color.textColors.onPrimary)
        }

        TaskContent(
            taskState = taskScreenUiState,
            onTaskTitleChanged = viewModel::onUpdateTaskTitle,
            onTaskDescriptionChanged = viewModel::onUpdateTaskDescription,
            onUpdateTaskDueDate = viewModel::onUpdateTaskDueDate,
            onUpdateTaskPriority = viewModel::onUpdateTaskPriority,
            onSelectTaskCategory = viewModel::onSelectTaskCategory,
            addButtonState = addButtonState,
            hideButtonSheet = viewModel::hideButtonSheet,
            isEditMode = false,
            onSaveClicked = viewModel::onSaveClicked,
            onAddClicked = viewModel::onAddClicked
        )
    }
}
