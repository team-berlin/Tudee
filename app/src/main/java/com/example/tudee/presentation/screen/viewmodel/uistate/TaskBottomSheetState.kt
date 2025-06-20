package com.example.tudee.presentation.viewmodel.uistate

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.datetime.LocalDate
import org.koin.androidx.viewmodel.scope.emptyState

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
    val categories: List<TaskCategory> = emptyList(),
    val taskPriorities: List<TaskPriority> = listOf(
        TaskPriority.HIGH,
        TaskPriority.MEDIUM,
        TaskPriority.LOW
    ),
    val isDatePickerVisible: Boolean=false,

    )



