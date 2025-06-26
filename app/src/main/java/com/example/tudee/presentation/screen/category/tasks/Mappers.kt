package com.example.tudee.presentation.screen.category.tasks

import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.category.model.UiImage
import com.example.tudee.presentation.utils.toCategoryIcon


fun Task.toTaskUIModel(): TaskUIModel {
    return TaskUIModel(
        id = this.id,
        categoryId = this.categoryId,
        title = this.title,
        description = this.description,
        priority = this.priority.toTaskPriorityUi(),
        status = this.status.toUiState(),
        assignedDate = this.assignedDate.toString()
    )
}

fun TaskPriority.toTaskPriorityUi(): TaskPriorityUiModel {
    return when (this) {
        TaskPriority.HIGH -> TaskPriorityUiModel.HIGH
        TaskPriority.MEDIUM -> TaskPriorityUiModel.MEDIUM
        TaskPriority.LOW -> TaskPriorityUiModel.LOW
    }
}

fun TaskCategory.toTaskCategoryUiModel(tasks: List<Task> = emptyList()): CategoryTasksUiModel {
    val uiImage: UiImage = if (isPredefined) {
        UiImage.Drawable(this.image.toCategoryIcon())
    } else {
        UiImage.External(image)
    }
    return CategoryTasksUiModel(
        id = this.id,
        title = this.title,
        image = uiImage,
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