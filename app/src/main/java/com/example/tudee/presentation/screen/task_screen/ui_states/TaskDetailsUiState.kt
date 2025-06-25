package com.example.tudee.presentation.screen.task_screen.ui_states

import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.task_screen.mappers.TaskPriorityUiState
import com.example.tudee.presentation.screen.task_screen.mappers.TaskStatusUiState

data class TaskDetailsUiState(
    val id: Long,
    val title: String,
    val description: String,
    val categoryIconRes: String,
    val priority: TaskPriorityUiState,
    val status: TaskStatusUiState,
)