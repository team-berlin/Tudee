package com.example.tudee.presentation.screen.categorytasks

import com.example.tudee.R
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority

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
        status = this.status,
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