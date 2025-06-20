package com.example.tudee.presentation.screen.category.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tudee.R
import com.example.tudee.presentation.components.TabBarItem

data class CategoryTasksUiState (
    val loading: Boolean,
    val categoryTasksUiModel: CategoryTasksUiModel? = null
)

data class CategoryTasksUiModel(
    val id: Long,
    val title: String,
    val image: String,
    val isPredefined: Boolean,
    val tasks: List<TaskUIModel>,
    val listOfTabBarItem: List<TabBarItem> = defaultTabBarItem,
    val selectedTabIndex: Int = 0,
)

data class TaskUIModel(
    val id: Long,
    val title: String,
    val description: String,
    val priority: TaskPriorityUiModel,
    val status: TaskStatusUiState,
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

enum class TaskStatusUiState(@StringRes var status: Int) {
    IN_PROGRESS(R.string.in_progress),
    TODO(R.string.to_do),
    DONE(R.string.done)
}

val defaultTabBarItem = listOf(
    TabBarItem(
        title = TaskStatusUiState.IN_PROGRESS.status, taskCount = "0", isSelected = true
    ),
    TabBarItem(
        title = TaskStatusUiState.TODO.status, taskCount = "0", isSelected = false
    ),
    TabBarItem(
        title = TaskStatusUiState.DONE.status, taskCount = "0", isSelected = false
    ),

    )