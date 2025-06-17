package com.example.tudee

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import kotlinx.datetime.LocalDateTime

data class AddNewTaskScreenState(
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskDueDate: LocalDateTime? = null,
    val taskPriority: TaskPriority? = null,
    val categories: List<TaskCategory> = emptyList(),
    val selectedCategoryId: Long? = null,
)