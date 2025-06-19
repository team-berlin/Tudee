package com.example.tudee.ui.home.viewmodel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class HomeUiState(
    //TODO : Ask about theme mode switch
    val taskUiState: TaskUiState = TaskUiState(),
    val taskTodayDateUiState: TaskTodayDateUiState = TaskTodayDateUiState(),
    val sliderUiState: SliderUiState = SliderUiState(),
    val tasksUiCount: TasksUiCount = TasksUiCount(),

    val todayTasksDoneCount: String = "",

    val todayTasksTodoCount: String = "",

    val todayTasksInProgressCount: String = "",

    val allTasks: List<TaskUiState> = emptyList(),
    val todayTasksInProgress: List<TaskUiState> = emptyList(),
    val todayTasksDone: List<TaskUiState> = emptyList(),
    val todayTasksTodo: List<TaskUiState> = emptyList(),

    val isBottomSheetVisible: Boolean = false,
    val isEditTaskBottomSheetContentVisible: Boolean = false,
    val selectedTask: TaskUiState? = null,
    val showSnackBar: Boolean = false,

    val navigateTodoTasks: Boolean = false,
    val navigateInProgressTasks: Boolean = false,
    val navigateDoneTasks: Boolean = false,


    )

data class SliderUiState(
    @StringRes
    val cardTitle: Int? = null,
    @StringRes
    val cardDescription: Int? = null,
    @DrawableRes
    val cardEmoji: Int? = null,
    @DrawableRes
    val cardImage: Int? = null,
)

data class TasksUiCount(
    val tasksDoneCount: String = "",
    val tasksTodoCount: String = "",
    val tasksInProgressCount: String = "",
)

data class TaskTodayDateUiState(
    val todayDateNumber: String = "",
    val month: String = "",
    val year: String = "",
)

