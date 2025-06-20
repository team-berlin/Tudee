package com.example.tudee.presentation.screen.task_screen.ui_states

import androidx.annotation.StringRes
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.TabBarItem
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.screen.task_screen.viewmodel.defaultTabBarItem
import com.example.tudee.presentation.screen.task_screen.viewmodel.getNextSevenDays
import com.example.tudee.presentation.viewmodel.taskuistate.TaskDetailsUiState
import kotlinx.datetime.LocalDate

data class TasksScreenUiState(
    val screenTitle: String = "",
    val date: String = "",
    val listOfDateCardUiState: List<DateCardUiState> = getNextSevenDays(),
    val listOfTabBarItem: List<TabBarItem> = defaultTabBarItem,
    val selectedTabIndex: Int = 0,
    val listOfTasksUiState: List<TaskUiState> = emptyList(),
    val isLoading: Boolean = true,
    val isBottomSheetVisible: Boolean = false,
    val isAddBottomSheetVisible: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val datePickerUiState: DatePickerUiState = DatePickerUiState(),
    val deleteBottomSheetUiState: DeleteBottomSheetUiState = DeleteBottomSheetUiState(),
    val idOfTaskToBeDeleted: Long = 0,
    val noCurrentTasks: Boolean = listOfTasksUiState.isEmpty(),
    val taskDetailsUiState: TaskDetailsUiState= TaskDetailsUiState(
        id = -100,
        title = "",
        description = "",
        categoryIconRes =0,
        priority = TaskPriority.LOW,
        status = TaskStatus.IN_PROGRESS
    )
)

data class DatePickerUiState(
    val isVisible: Boolean = false,
    val selectedDate: LocalDate? = null
)

data class TaskUiState(
    val id: Long,
    val title: String,
    val description: String,

    @StringRes
    val priority: Int,
    @StringRes
    val status: Int,
    val categoryTitle: String,

    val categoryIcon: String
)

data class DateCardUiState(
    val dayNumber: String,
    val dayName: String,
    val isSelected: Boolean
)

data class DeleteBottomSheetUiState(
    val title: String = "Delete Task",
    val subtitle: String = "Are you sure to continue?",
    val deleteButtonState: ButtonState = ButtonState.IDLE,
    val cancelButtonState: ButtonState = ButtonState.IDLE,
)
