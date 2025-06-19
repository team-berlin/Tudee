package com.example.tudee.presentation.screen.task_screen.mappers

import androidx.annotation.StringRes
import com.example.tudee.R
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState


enum class TaskStatusUiState(@StringRes var status: Int) {
    IN_PROGRESS(R.string.in_progress),
    TODO(R.string.todo),
    DONE(R.string.done)
}


enum class TaskPriorityUiState(@StringRes var priority: Int) {
    LOW(R.string.low),
    MEDIUM(R.string.medium),
    HIGH(R.string.high)
}

fun TaskStatus.toUiState(): TaskStatusUiState = when (this) {
    TaskStatus.TODO -> TaskStatusUiState.TODO
    TaskStatus.IN_PROGRESS -> TaskStatusUiState.IN_PROGRESS
    TaskStatus.DONE -> TaskStatusUiState.DONE
}

fun TaskStatusUiState.toDomain(): TaskStatus = when (this) {
    TaskStatusUiState.TODO -> TaskStatus.TODO
    TaskStatusUiState.IN_PROGRESS -> TaskStatus.IN_PROGRESS
    TaskStatusUiState.DONE -> TaskStatus.DONE
}

fun TaskPriorityUiState.toDomain(): TaskPriority = when (this) {
    TaskPriorityUiState.HIGH -> TaskPriority.HIGH
    TaskPriorityUiState.MEDIUM -> TaskPriority.MEDIUM
    TaskPriorityUiState.LOW -> TaskPriority.LOW
}

fun TaskPriority.toUiState(): TaskPriorityUiState = when (this) {
    TaskPriority.HIGH -> TaskPriorityUiState.HIGH
    TaskPriority.MEDIUM -> TaskPriorityUiState.MEDIUM
    TaskPriority.LOW -> TaskPriorityUiState.LOW
}



fun Task.taskToTaskUiState(categoryIcon: Int): TaskUiState {
    return TaskUiState(
        id = id,
        title = title,
        description = description,
        priority = priority.toUiState().priority,
        status = status.toUiState().status,
        categoryTitle = "",
        categoryIcon = categoryIcon.toString()
    )
}