package com.example.tudee.ui.home.viewmodel

import androidx.annotation.StringRes
import com.example.tudee.R
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.datetime.LocalDate

data class TaskUiState(
    val taskId: String = "",
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskPriority: TaskPriorityUiState = TaskPriorityUiState.MEDIUM,
    val taskCategory: CategoryUiState=CategoryUiState(),
    val taskStatusUiState: TaskStatusUiState = TaskStatusUiState.TODO,
    val taskAssignedDate: LocalDate = LocalDate(2023, 1, 1),
)
enum class TaskStatusUiState(@StringRes status:Int){
    TODO(R.string.todo),
    IN_PROGRESS(R.string.in_progress),
    DONE(R.string.done)
}
enum class TaskPriorityUiState(@StringRes priority:Int){
    LOW(R.string.low),
    MEDIUM(R.string.medium),
    HIGH(R.string.high)
}