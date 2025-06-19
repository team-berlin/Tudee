package com.example.tudee.ui.mapper

import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.ui.home.viewmodel.TaskPriorityUiState
import com.example.tudee.ui.home.viewmodel.TaskStatusUiState
import com.example.tudee.ui.home.viewmodel.TaskUiState
import kotlinx.datetime.LocalDate

fun Task.toTaskUiState(): TaskUiState = TaskUiState(
    taskId = id.toString(),
    taskTitle = title,
    taskDescription = description,
    taskPriority = priority.toTaskPriorityUiState(),
    taskStatusUiState = status.toTaskStatusUiState(),
    taskAssignedDate = assignedDate
)
fun TaskUiState.toTask(): Task = Task(
    id = taskId.toLong(),
    title = taskTitle,
    description = taskDescription,
    categoryId = taskCategory.id.toLong(),
    priority = taskPriority.toTaskPriority(),
    assignedDate = taskAssignedDate,
    status = taskStatusUiState.toTaskStatus()
)
fun LocalDate.formatDate(): String {
    return "${dayOfMonth}-${monthNumber}-${year}"
}
fun TaskStatus.toTaskStatusUiState(): TaskStatusUiState = when (this) {
    TaskStatus.TODO -> TaskStatusUiState.TODO
    TaskStatus.IN_PROGRESS -> TaskStatusUiState.IN_PROGRESS
    TaskStatus.DONE -> TaskStatusUiState.DONE
}

fun TaskStatusUiState.toTaskStatus(): TaskStatus = when (this) {
    TaskStatusUiState.TODO -> TaskStatus.TODO
    TaskStatusUiState.IN_PROGRESS -> TaskStatus.IN_PROGRESS
    TaskStatusUiState.DONE -> TaskStatus.DONE
}
fun TaskPriorityUiState.toTaskPriority(): TaskPriority = when (this) {
    TaskPriorityUiState.LOW -> TaskPriority.LOW
    TaskPriorityUiState.MEDIUM -> TaskPriority.MEDIUM
    TaskPriorityUiState.HIGH -> TaskPriority.HIGH
}
fun TaskPriority.toTaskPriorityUiState(): TaskPriorityUiState = when (this) {
    TaskPriority.LOW -> TaskPriorityUiState.LOW
    TaskPriority.MEDIUM -> TaskPriorityUiState.MEDIUM
    TaskPriority.HIGH -> TaskPriorityUiState.HIGH
}