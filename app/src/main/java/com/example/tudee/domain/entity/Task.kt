package com.example.tudee.domain.entity

import kotlinx.datetime.LocalDate

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val categoryId: Long,
    val status: TaskStatus,
    val assignedDate: LocalDate,
)


