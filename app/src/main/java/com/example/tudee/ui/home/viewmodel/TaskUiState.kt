package com.example.tudee.ui.home.viewmodel

import androidx.annotation.StringRes
import com.example.tudee.R
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TaskUiState(
    val taskId: String = "",
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskPriority: TaskPriorityUiState = TaskPriorityUiState.MEDIUM,
    val taskCategory: CategoryUiState = CategoryUiState(),
    val taskStatusUiState: TaskStatusUiState = TaskStatusUiState.TODO,
    val taskAssignedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
)
enum class TaskStatusUiState(@StringRes val status:Int){
    TODO(R.string.todo),
    IN_PROGRESS(R.string.in_progress),
    DONE(R.string.done)
}
enum class TaskPriorityUiState(@StringRes val  priority:Int){
    LOW(R.string.low),
    MEDIUM(R.string.medium),
    HIGH(R.string.high)
}