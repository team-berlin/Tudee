package com.example.tudee.presentation.viewmodel.taskuistate

import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus

data class TaskDetailsUiState(
    val id: Long,
    val title: String,
    val description: String,
    val categoryIconRes: String,
    val priority: TaskPriority,
    val status: TaskStatus,
)