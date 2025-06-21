package com.example.tudee.presentation.screen.home

import com.example.tudee.data.model.TaskEntity
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.home.viewmodel.TaskPriorityUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskStatusUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

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

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.name,
        categoryId = this.categoryId,
        status = this.status.name,
        assignedDate = this.assignedDate.toString()
    )
}

fun TaskEntity.toDomain(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = TaskPriority.valueOf(this.priority),
        categoryId = this.categoryId,
        status = TaskStatus.valueOf(this.status),
        assignedDate = this.assignedDate.toLocalDate()
    )
}

fun TaskPriority.toTaskPriorityUiState(): TaskPriorityUiState {
    return when (this) {
        TaskPriority.LOW -> TaskPriorityUiState.LOW
        TaskPriority.MEDIUM -> TaskPriorityUiState.MEDIUM
        TaskPriority.HIGH -> TaskPriorityUiState.HIGH
    }
}

fun Flow<List<TaskEntity>>.toDomain(): Flow<List<Task>> {
    return map { it.map(TaskEntity::toDomain) }
}