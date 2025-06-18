package com.example.tudee

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import kotlinx.datetime.LocalDate

data class TaskScreenState(
    val showBottomSheet: Boolean = false,
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskDueDate: LocalDate? = null,
    val selectedTaskPriority: TaskPriority? = null,
    val categories: List<TaskCategory> = emptyList(),
    val selectedCategoryId: Long? = null,
    val taskPriorities: List<TaskPriority> = listOf(
        TaskPriority.HIGH,
        TaskPriority.MEDIUM,
        TaskPriority.LOW
    ),
)