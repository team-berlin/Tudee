package com.example.tudee.presentation.screen.task_screen.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.color.TudeeColors
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState


enum class TaskStatusUiState(
    @StringRes val label: Int,
    private val contentColorProvider: ColorType,
    private val containerColorProvider: ColorType,
) {
    IN_PROGRESS(label=R.string.in_progress
        ,containerColorProvider = { TudeeTheme.color.statusColors.purpleVariant }
        ,contentColorProvider = { TudeeTheme.color.statusColors.purpleAccent }
    ),

    TODO(label=R.string.todo
        ,containerColorProvider = { TudeeTheme.color.statusColors.yellowVariant }
        ,contentColorProvider = { TudeeTheme.color.statusColors.yellowAccent }
    ),
    DONE(label=R.string.done
        ,containerColorProvider = { TudeeTheme.color.statusColors.greenVariant }
        ,contentColorProvider = { TudeeTheme.color.statusColors.greenAccent }
    );
    val containerColor: Color
        @Composable get() = containerColorProvider()

    val contentColor: Color
        @Composable get() = contentColorProvider()

}
typealias ColorType = @Composable () -> Color


enum class TaskPriorityUiState(
    @StringRes val label: Int,
    private val contentColorProvider: ColorType,
    private val containerColorProvider: ColorType,
    @DrawableRes val icon: Int,
) {
    LOW(label=R.string.low_priority
        ,containerColorProvider = { TudeeTheme.color.statusColors.pinkAccent }
        ,contentColorProvider = { TudeeTheme.color.textColors.onPrimary }
        , icon = R.drawable.ic_priority_low
    ),

    MEDIUM(label=R.string.medium_priority
        ,containerColorProvider = { TudeeTheme.color.statusColors.yellowAccent }
        ,contentColorProvider = { TudeeTheme.color.textColors.onPrimary }
        , icon = R.drawable.ic_priority_medium
    ),
    HIGH(label=R.string.high_priority
        ,containerColorProvider = { TudeeTheme.color.statusColors.greenAccent }
        ,contentColorProvider = { TudeeTheme.color.textColors.onPrimary}
        , icon = R.drawable.ic_priority_high
    );
    val containerColor: Color
        @Composable get() = containerColorProvider()

    val contentColor: Color
        @Composable get() = contentColorProvider()
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


fun Task.taskToTaskUiState(categoryIcon: String): TaskUiState {
    return TaskUiState(
        id = id,
        title = title,
        description = description,
        priority = priority.toUiState(),
        status = status.toUiState(),
        categoryTitle = "categorytitel",
        categoryIcon = categoryIcon
    )
}