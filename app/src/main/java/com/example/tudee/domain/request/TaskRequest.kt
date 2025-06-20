package com.example.tudee.domain.request

import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.datetime.LocalDate

data class TaskCreationRequest(
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val categoryId: Long,
    val status: TaskStatus,
    val assignedDate: LocalDate
){
fun validateTitle() = {
    title.isNotEmpty()
}

    fun validateDescription() = {
        description.isNotEmpty()
    }
//    fun validatePriority() = {
//        priority != TaskPriority.NONE
//    }
    fun validateCategoryId() = {
        categoryId != 0L
    }
}
