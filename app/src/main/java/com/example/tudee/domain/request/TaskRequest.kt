package com.example.tudee.domain.request

import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus

data class TaskCreationRequest(
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val categoryId: Long,
    val status: TaskStatus,
    val assignedDate: String
)

