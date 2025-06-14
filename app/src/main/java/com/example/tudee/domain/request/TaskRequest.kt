package com.example.tudee.domain.request

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.datetime.LocalDate

data class TaskRequest(
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val category: TaskCategory?,
    val status: TaskStatus,
    val assignedDate: LocalDate
)

