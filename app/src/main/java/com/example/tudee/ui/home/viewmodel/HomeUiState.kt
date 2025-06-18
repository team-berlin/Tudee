package com.example.tudee.ui.home.viewmodel

import androidx.annotation.StringRes


data class HomeUiState(
    val taskUiState: TaskUiState = TaskUiState(),
    val taskTodayDateUiState: TaskTodayDateUiState = TaskTodayDateUiState(),
    val overAllCardUiState: OverAllCardUiState = OverAllCardUiState(),
    val tasksDoneCount: String = "",
    val tasksTodoCount: String = "",
    val tasksInProgressCount: String = "",
    val todayTotalTasksCount: String = "",
    val todayTasksInProgress: List<TaskUiState> = emptyList(),
    val todayTasksDone: List<TaskUiState> = emptyList(),
    val todayTasksTodo: List<TaskUiState> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
    val isEditTaskBottomSheetContentVisible: Boolean = false,
    val showSnackBar : Boolean= false
)
data class OverAllCardUiState(
    val cardTitle: String = "",
    val cardDescription: String = "",
    val cardEmoji: Int = 0,
    val cardImage: Int = 0,
)

data class TaskTodayDateUiState(
    val todayDateNumber: String = "",
    val month: String = "",
    val year: String = "",
)