package com.example.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.Destination
import com.example.tudee.naviagtion.Destination.CategoriesScreen
import com.example.tudee.naviagtion.Destination.TasksScreen
import com.example.tudee.naviagtion.TudeeNavGraph
import com.example.tudee.presentation.screen.TaskContent
import com.example.tudee.presentation.screen.taskscreen.addTask.AddBottomSheet
import com.example.tudee.presentation.screen.taskscreen.editTask.EditeBottomSheet
import com.example.tudee.presentation.viewmodel.TaskViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TudeeTheme {
                val viewModel: TaskViewModel = koinViewModel()
                val taskScreenUiState by viewModel.uiState.collectAsState()
                val addButtonState by viewModel.isTaskValid.collectAsState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    android.util.Log.d("MainScreen", "Add button clicked")
                                    viewModel.showButtonSheet()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                enabled = true
                            ) {
                                Text("Add Task", color = TudeeTheme.color.textColors.onPrimary)
                            }

                            Button(
                                onClick = {
                                    android.util.Log.d("MainScreen", "Edit button clicked")
                                    viewModel.run {
                                        showButtonSheet()
                                        getTaskInfoById(3L)
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
                                isEditMode = taskScreenUiState.isEditMode,
                                onSaveClicked = viewModel::onSaveClicked,
                                onAddClicked = viewModel::onAddNewTaskClicked,
                                onCancelButtonClicked = viewModel::onCancelClicked
                            )

                        }
                    }
                )
            }
        }
    }
}


