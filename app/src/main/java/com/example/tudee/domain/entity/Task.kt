package com.example.tudee.domain.entity

import kotlinx.datetime.LocalDate

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val category: TaskCategory?,
    val status: TaskStatus,
    val assignedDate: LocalDate,
)