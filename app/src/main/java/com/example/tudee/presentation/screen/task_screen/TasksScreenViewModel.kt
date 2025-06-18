package com.example.tudee.presentation.screen.task_screen

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.R
import com.example.tudee.data.model.TaskCategoryEntity
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.TabBarItem
import com.example.tudee.presentation.composables.buttons.ButtonState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId

class TasksScreenViewModel(
    private val taskService: TaskService
) : ViewModel(), TaskScreenInteractor {
    private val _taskScreenUiState = MutableStateFlow(TasksScreenUiState())
    val taskScreenUiState = _taskScreenUiState

    init {
        viewModelScope.launch {
            getTasksByStatus(TaskStatus.TODO)
        }
    }

    override fun onDayCardClicked(cardIndex: Int) {
        _taskScreenUiState.update {
            it.copy(
                listOfDateCardUiState = it.listOfDateCardUiState.mapIndexed { i, card ->
                    card.copy(isSelected = cardIndex == i)
                })
        }

    }

    override fun onDeleteIconClicked(idOfTaskToBeDeleted: Long?) {
        _taskScreenUiState.update {
            it.copy(isBottomSheetVisible = true, idOfTaskToBeDeleted = idOfTaskToBeDeleted)
        }
    }

    override fun onDeleteButtonClicked() {
        _taskScreenUiState.update { state ->
            state.copy(
                deleteBottomSheetUiState = state.deleteBottomSheetUiState.copy(
                    deleteButtonState = ButtonState.LOADING
                )
            )
        }
        viewModelScope.launch {
            delay(500)
            _taskScreenUiState.update { state ->
                state.copy(
                    listOfTasksUiState = state.listOfTasksUiState.filterNot { it.id == state.idOfTaskToBeDeleted },
                    isBottomSheetVisible = false,
                    deleteBottomSheetUiState = state.deleteBottomSheetUiState.copy(
                        deleteButtonState = ButtonState.IDLE
                    )
                )
            }
            onSnackBarShown()
        }

    }

    override fun onCancelButtonClicked() {
        _taskScreenUiState.update {
            it.copy(isBottomSheetVisible = false)
        }
    }

    override fun onBottomSheetDismissed() {
        _taskScreenUiState.update {
            viewModelScope.launch { delay(400) }
            it.copy(isBottomSheetVisible = false)
        }
    }

    override fun onTabSelected(tabIndex: Int) {
        _taskScreenUiState.update { taskScreenUiState ->
            taskScreenUiState.copy(
                selectedTabIndex = tabIndex,
                listOfTasksUiState = dummyTasks.filter {
                    it.status.toString() == taskScreenUiState.listOfTabBarItem[tabIndex].title
                },
                listOfTabBarItem = taskScreenUiState.listOfTabBarItem.mapIndexed { index, tabItem ->
                    tabItem.copy(
                        taskCount = dummyTasks.filter {
                            it.status.toString() == taskScreenUiState.listOfTabBarItem[tabIndex].title
                        }.size.toString(), isSelected = tabIndex == index
                    )
                },

                )
        }
    }

    override fun onFloatingActionClicked() {

    }

    override fun onTaskCardClicked(taskId: Long) {

    }

    override fun onDateCardClicked() {
        _taskScreenUiState
            .update {
                it.copy(
                    datePickerUiState = it.datePickerUiState.copy(isVisible = true)
                )
            }
    }
    override fun onDismissDatePicker() {
        _taskScreenUiState.update {
            it.copy(
                datePickerUiState = it.datePickerUiState.copy(isVisible = false)
            )
        }
    }

    fun onConfirmDatePicker(millis: Long?) {
        val date = millis?.let {
            Instant.fromEpochMilliseconds(it)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
        }

        _taskScreenUiState.update {
            it.copy(
                datePickerUiState = it.datePickerUiState.copy(isVisible = false, selectedDate = date))
        }

    }

    suspend fun onSnackBarShown() {
        _taskScreenUiState.update {
            it.copy(isSnackBarVisible = true)
        }

        delay(1000)

        _taskScreenUiState.update {
            it.copy(isSnackBarVisible = false)

        }
    }

    private suspend fun getTasksByStatus(status: TaskStatus) {
        _taskScreenUiState.update { uiState ->
            uiState.copy(
                listOfTasksUiState = dummyTasks.filter { it.status == status.toUiState().status }

            )
        }
    }
}

data class TasksScreenUiState(
    val screenTitle: String = "",
    val date: String = "",
    val listOfDateCardUiState: List<DateCardUiState> = getNextSevenDays(),
    val listOfTabBarItem: List<TabBarItem> = defaultTabBarItem,
    val selectedTabIndex: Int = 0,
    val listOfTasksUiState: List<TaskUiState> = emptyList(),
    val isLoading: Boolean = true,
    val isBottomSheetVisible: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val datePickerUiState: DatePickerUiState =DatePickerUiState(),
    val deleteBottomSheetUiState: DeleteBottomSheetUiState = DeleteBottomSheetUiState(),
    val idOfTaskToBeDeleted: Long? = null,
    val noCurrentTasks: Boolean = listOfTasksUiState.isEmpty()
)

data class DatePickerUiState(
    val isVisible: Boolean = false,
    val selectedDate: LocalDate? = null
)

data class TaskUiState(
    val id: Long,
    val title: String,
    val description: String,
    val priority: String,
    val status: Int,
    val categoryTitle: String,
    val categoryIcon: String 
    )

data class DateCardUiState(
    val dayNumber: String, val dayName: String, val isSelected: Boolean
)


private val dummyTasks = listOf(
    Task(
        1L,
        "Design Login UI",
        "Create a clean login screen",
        TaskPriority.HIGH,
        101L,
        TaskStatus.TODO,
        "2025-06-16".toLocalDate()
    ), Task(
        2L,
        "Write Unit Tests",
        "Cover the authentication module",
        TaskPriority.MEDIUM,
        102L,
        TaskStatus.IN_PROGRESS,
        "2025-06-15".toLocalDate()
    ), Task(
        3L,
        "Fix Navigation Bug",
        "App crashes on back press",
        TaskPriority.HIGH,
        103L,
        TaskStatus.TODO,
        "2025-06-14".toLocalDate()
    ), Task(
        4L,
        "Optimize DB Queries",
        "Improve Room performance",
        TaskPriority.LOW,
        104L,
        TaskStatus.DONE,
        "2025-06-12".toLocalDate()
    ), Task(
        5L,
        "Refactor ViewModel",
        "Split into smaller modules",
        TaskPriority.MEDIUM,
        101L,
        TaskStatus.IN_PROGRESS,
        "2025-06-11".toLocalDate()
    ), Task(
        6L,
        "Update Dependencies",
        "Bring all libs to latest stable",
        TaskPriority.LOW,
        105L,
        TaskStatus.TODO,
        "2025-06-10".toLocalDate()
    ), Task(
        7L,
        "Write Docs",
        "Document public APIs",
        TaskPriority.MEDIUM,
        102L,
        TaskStatus.DONE,
        "2025-06-09".toLocalDate()
    ), Task(
        8L,
        "Setup CI",
        "Configure GitHub Actions",
        TaskPriority.HIGH,
        106L,
        TaskStatus.IN_PROGRESS,
        "2025-06-08".toLocalDate()
    ), Task(
        9L,
        "Design Settings Page",
        "Include themes, preferences",
        TaskPriority.MEDIUM,
        103L,
        TaskStatus.TODO,
        "2025-06-07".toLocalDate()
    ), Task(
        10L,
        "Implement Logout",
        "Clear session, redirect user",
        TaskPriority.LOW,
        104L,
        TaskStatus.DONE,
        "2025-06-06".toLocalDate()
    ), Task(
        11L,
        "Create Splash Screen",
        "Add branding and loader",
        TaskPriority.MEDIUM,
        105L,
        TaskStatus.TODO,
        "2025-06-05".toLocalDate()
    ), Task(
        12L,
        "Improve Animations",
        "Add transitions to views",
        TaskPriority.LOW,
        106L,
        TaskStatus.IN_PROGRESS,
        "2025-06-04".toLocalDate()
    ), Task(
        13L,
        "Fix Dark Mode Bugs",
        "Icons invisible in dark mode",
        TaskPriority.HIGH,
        101L,
        TaskStatus.TODO,
        "2025-06-03".toLocalDate()
    ), Task(
        14L,
        "Create User Profile",
        "UI for name, avatar, bio",
        TaskPriority.MEDIUM,
        102L,
        TaskStatus.IN_PROGRESS,
        "2025-06-02".toLocalDate()
    ), Task(
        15L,
        "Enable Push Notifications",
        "Notify on important events",
        TaskPriority.HIGH,
        103L,
        TaskStatus.DONE,
        "2025-06-01".toLocalDate()
    )
).map { it.taskToTaskUiState() }


fun Task.taskToTaskUiState(): TaskUiState {
    return TaskUiState(
        id = id,
        title = title,
        description = description,
        priority = priority.toString(),
        status = status.toUiState().status,
        categoryTitle ="",
        categoryIcon = ""
    )
}

fun getNextSevenDays(): List<DateCardUiState> {
    val today: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    return (0 until 7).map { offset ->
        val date = today.plus(offset, DateTimeUnit.DAY)
        val dayOfMonth = date.dayOfMonth.toString()

        val dayName = date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
        val halfName = dayName.substring(0, 3)
        DateCardUiState(
            dayNumber = dayOfMonth, dayName = halfName, isSelected = false
        )

    }
}

val defaultTabBarItem = listOf(
    TabBarItem(
        title = TaskStatusUiState.IN_PROGRESS.status.toString(), taskCount = "0", isSelected = false
    ),
    TabBarItem(
        title = TaskStatusUiState.TODO.status.toString(), taskCount = "0", isSelected = false
    ),
    TabBarItem(
        title = TaskStatusUiState.DONE.status.toString(), taskCount = "0", isSelected = false
    ),

    )

data class DeleteBottomSheetUiState(
    val title: String = "Delete Task",
    val subtitle: String = "Are you sure to continue?",
    val deleteButtonState: ButtonState = ButtonState.IDLE,
    val cancelButtonState: ButtonState = ButtonState.IDLE,
)

enum class TaskStatusUiState(@StringRes var status: Int) {
    TODO(R.string.todo),
    IN_PROGRESS(R.string.in_progress),
    DONE(R.string.done)
}

enum class TaskPriorityUiState(@StringRes priority: Int) {
    LOW(R.string.low), MEDIUM(R.string.medium), HIGH(R.string.high)
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
