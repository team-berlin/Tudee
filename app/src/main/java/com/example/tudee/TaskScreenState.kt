package com.example.tudee

import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.datetime.LocalDate

data class TaskScreenState(
    val showBottomSheet: Boolean = false,
    val taskId: Long? = null,
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskDueDate: LocalDate? = null,
    val selectedTaskPriority: TaskPriority? = null,
    val taskStatus: TaskStatus? = null,
    val selectedCategoryId: Long? = null,
    val isEditMode: Boolean = false,

    val categories: List<TaskCategory> = emptyList(),
    val taskPriorities: List<TaskPriority> = listOf(
        TaskPriority.HIGH,
        TaskPriority.MEDIUM,
        TaskPriority.LOW
    ),
)