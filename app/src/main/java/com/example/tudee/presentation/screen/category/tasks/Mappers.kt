package com.example.tudee.presentation.screen.category.tasks

import com.example.tudee.R
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus

fun TaskPriority.toTaskPriorityUi(): TaskPriorityUiModel {
    return when (this) {
        TaskPriority.HIGH -> {
            TaskPriorityUiModel(
                tasPriorityType = TaskPriorityType.HIGH,
                priorityTextId = R.string.high_priority,
                priorityIconDrawable = R.drawable.ic_priority_high,
            )
        }

        TaskPriority.MEDIUM -> {
            TaskPriorityUiModel(
                tasPriorityType = TaskPriorityType.MEDIUM,
                priorityTextId = R.string.medium_priority,
                priorityIconDrawable = R.drawable.ic_priority_medium,
            )
        }

        TaskPriority.LOW -> {
            TaskPriorityUiModel(
                tasPriorityType = TaskPriorityType.LOW,
                priorityTextId = R.string.low_priority,
                priorityIconDrawable = R.drawable.ic_priority_low,
            )
        }
    }
}

fun Task.toTaskUIModel(): TaskUIModel {
    return TaskUIModel(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.toTaskPriorityUi(),
        status = this.status.toUiState(),
        assignedDate = this.assignedDate.toString()
    )
}

fun TaskCategory.toTaskCategoryUiModel(tasks: List<Task>): CategoryTasksUiModel {
    return CategoryTasksUiModel(
        id = this.id,
        title = this.title,
        image = this.image,
        isPredefined = this.isPredefined,
        tasks = tasks.map { it.toTaskUIModel() }
    )
}

fun TaskStatus.toUiState(): TaskStatusUiState = when (this) {
    TaskStatus.TODO -> TaskStatusUiState.TODO
    TaskStatus.IN_PROGRESS -> TaskStatusUiState.IN_PROGRESS
    TaskStatus.DONE -> TaskStatusUiState.DONE
}

fun TaskStatusUiState.toDomain() = when (this) {
    TaskStatusUiState.IN_PROGRESS -> TaskStatus.IN_PROGRESS
    TaskStatusUiState.TODO -> TaskStatus.TODO
    TaskStatusUiState.DONE -> TaskStatus.DONE
}