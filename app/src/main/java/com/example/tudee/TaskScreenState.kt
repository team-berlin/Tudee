package com.example.tudee

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.datetime.LocalDate

data class TaskScreenState(
    val isButtonSheetVisibile: Boolean = false,
    val taskId: Long? = null,
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskDueDate: LocalDate? = null,
    val selectedTaskPriority: TaskPriority? = null,
    val taskStatus: TaskStatus? = null,
    val selectedCategoryId: Long? = null,
    val isEditMode: Boolean = false,
    val avtioType: ButtonType = ButtonType.ADD,

    val categories: List<TaskCategory> = listOf<TaskCategory>(
        TaskCategory(
            id = 0L,
            title = "Work",
            isPredefined = true,
            image = "drawable/ic_work"
        ),
        TaskCategory(
            id = 1L,
            title = "Personal",
            isPredefined = true,
            image = "drawable/ic_personal"
        ),
        TaskCategory(
            id = 2L,
            title = "Shopping",
            isPredefined = true,
            image = "drawable/ic_shopping"
        )
    ),
    val taskPriorities: List<TaskPriority> = listOf(
        TaskPriority.HIGH,
        TaskPriority.MEDIUM,
        TaskPriority.LOW
    ),
)

enum class ButtonType {
    ADD,
    SAVE

}
