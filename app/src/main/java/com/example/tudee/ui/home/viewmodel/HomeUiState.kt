package com.example.tudee.ui.home.viewmodel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tudee.R


data class HomeUiState(
    val isDarkMode: Boolean = false,
    val taskUiState: TaskUiState = TaskUiState(),
    val taskTodayDateUiState: TaskTodayDateUiState = TaskTodayDateUiState(),
    val sliderUiState: SliderUiState = SliderUiState(),
    val tasksUiCount: TasksUiCount = TasksUiCount(),

    val allTasks: List<TaskUiState> = emptyList(),
    val todayTasksInProgress: List<TaskUiState> = emptyList(),
    val todayTasksDone: List<TaskUiState> = emptyList(),
    val todayTasksTodo: List<TaskUiState> = emptyList(),

    val isBottomSheetVisible: Boolean = false,
    val isEditTaskBottomSheetContentVisible: Boolean = false,
    val selectedTask: TaskUiState = TaskUiState(),
    val showSnackBar: Boolean = false,

    val navigateTodoTasks: Boolean = false,
    val navigateInProgressTasks: Boolean = false,
    val navigateDoneTasks: Boolean = false
)
data class SliderUiState(
    val sliderUiEnum: SliderEnum = SliderEnum.NOTHING_IN_YOUR_LIST,
    @StringRes val description: Int = R.string.nothing_in_your_list,
    val totalTasks: Int = 0,
    val doneTasks: Int = 0
)

enum class SliderEnum(
    @StringRes val title: Int,
    @DrawableRes val image: Int,
    @DrawableRes val emoji: Int,
) {
    STAY_WORKING(
        title = R.string.stay_working,
     //   description = R.string.stay_working_desc,
        image = R.drawable.tudee_stay_working_or_nothing,
        emoji = R.drawable.emoji_stay_working
    ),
    TADAA(
        title = R.string.Tadaa,
      //  description = R.string.Tadaa_desc,
        image = R.drawable.tudee,
        emoji = R.drawable.emoji_tadaa
    ),
    NOTHING_IN_YOUR_LIST(
        title = R.string.nothing_in_your_list,
    //    description = R.string.nothing_in_your_list_desc,
        image = R.drawable.tudee_stay_working_or_nothing,
        emoji = R.drawable.emoji_nothing
    ),
    ZERO_PROGRESS(
        title = R.string.zero_progress,
    //    description = R.string.zero_progress_desc,
        image = R.drawable.tudee_zero_progress,
        emoji = R.drawable.emoji_zero_progress
    )
}

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
