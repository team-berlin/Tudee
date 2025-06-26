package com.example.tudee.presentation.screen.task_screen.ui_states

import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.home.viewmodel.CategoryUiState

data class TaskBottomSheetState(
    val isButtonSheetVisible: Boolean = false,
    val taskId: Long? = null,
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskDueDate: String? = null,
    val selectedTaskPriority: TaskPriority? = null,
    val taskStatus: TaskStatus? = null,
    val selectedCategoryId: Long? = null,
    val isEditMode: Boolean = false,
    val snackBarMessage: Boolean? = null,
    val categories: List<CategoryUiState> = emptyList(),
    val taskPriorities: List<TaskPriority> = listOf(
        TaskPriority.HIGH,
        TaskPriority.MEDIUM,
        TaskPriority.LOW
    ),
    val isDatePickerVisible: Boolean=false,

    )

