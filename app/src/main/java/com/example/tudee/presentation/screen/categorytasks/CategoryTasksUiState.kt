package com.example.tudee.presentation.screen.categorytasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tudee.domain.entity.TaskStatus

sealed class CategoryTasksUiState {
    data object Loading : CategoryTasksUiState()
    data class Success(val categoryTasksUiModel: CategoryTasksUiModel) : CategoryTasksUiState()
}

data class CategoryTasksUiModel(
    val id: Long,
    val title: String,
    val image: String,
    val isPredefined: Boolean,
    val tasks: List<TaskUIModel>
)

data class TaskUIModel(
    val id: Long,
    val title: String,
    val description: String,
    val priority: TaskPriorityUiModel,
    val status: TaskStatus,
    val assignedDate: String
)

data class TaskPriorityUiModel(
    val tasPriorityType: TaskPriorityType,
    @StringRes val priorityTextId: Int,
    @DrawableRes val priorityIconDrawable: Int,
)

enum class TaskPriorityType {
    HIGH,
    MEDIUM,
    LOW;
}
