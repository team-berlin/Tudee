package com.example.tudee.presentation.screen.category.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.TabBarItem
import com.example.tudee.presentation.screen.category.model.UiImage

data class CategoryTasksUiState(
    val loading: Boolean = false,
    val isEditCategorySheetVisible: Boolean = false,
    val isDeleteCategorySheetVisible: Boolean = false,
    val categoryTasksUiModel: CategoryTasksUiModel? = null
)

data class CategoryTasksUiModel(
    val id: Long,
    val title: String,
    val image: UiImage,
    val isPredefined: Boolean,
    val tasks: List<TaskUIModel> = emptyList(),
    val listOfTabBarItem: List<TabBarItem> = defaultTabBarItem,
    val selectedTabIndex: Int = 0,
)

data class TaskUIModel(
    val id: Long,
    val categoryId: Long,
    val title: String,
    val description: String,
    val priority: TaskPriorityUiModel,
    val status: TaskStatusUiState,
    val assignedDate: String
)

enum class TaskPriorityUiModel(
    @StringRes val labelRes: Int,
    @DrawableRes val drawableRes: Int,
    private val containerColorProvider: @Composable () -> Color
) {
    HIGH(
        labelRes = R.string.high_priority,
        drawableRes = R.drawable.ic_priority_high,
        containerColorProvider = { TudeeTheme.color.statusColors.pinkAccent }
    ),
    MEDIUM(
        labelRes = R.string.medium_priority,
        drawableRes = R.drawable.ic_priority_medium,
        containerColorProvider = { TudeeTheme.color.statusColors.yellowAccent }
    ),
    LOW(
        labelRes = R.string.low_priority,
        drawableRes = R.drawable.ic_priority_low,
        containerColorProvider = { TudeeTheme.color.statusColors.greenAccent }
    );

    @Composable
    fun getContainerColor(): Color = containerColorProvider()
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