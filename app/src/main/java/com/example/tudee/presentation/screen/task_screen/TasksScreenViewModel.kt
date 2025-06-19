package com.example.tudee.presentation.screen.task_screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.tudee.R
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
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
import kotlinx.datetime.toLocalDateTime

class TasksScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: TaskCategoryService
) : ViewModel(), TaskScreenInteractor {
    private val _taskScreenUiState = MutableStateFlow(TasksScreenUiState())
    val taskScreenUiState = _taskScreenUiState

    init {
        viewModelScope.launch {
            taskService.createTask(
                TaskCreationRequest(
                    title = "First Task",
                    description = "Finish the initial UI design",
                    priority = TaskPriority.LOW,
                    categoryId = 1,
                    status = TaskStatus.TODO,
                    assignedDate = LocalDate(2023, 12, 12)
                )
            )

            taskService.createTask(
                TaskCreationRequest(
                    title = "Second Task",
                    description = "Set up authentication module",
                    priority = TaskPriority.MEDIUM,
                    categoryId = 1,
                    status = TaskStatus.IN_PROGRESS,
                    assignedDate = LocalDate(2023, 12, 13)
                )
            )

            taskService.createTask(
                TaskCreationRequest(
                    title = "Third Task",
                    description = "Write unit tests for login",
                    priority = TaskPriority.HIGH,
                    categoryId = 1,
                    status = TaskStatus.TODO,
                    assignedDate = LocalDate(2023, 12, 14)
                )
            )

            taskService.createTask(
                TaskCreationRequest(
                    title = "Fourth Task",
                    description = "Deploy to staging environment",
                    priority = TaskPriority.MEDIUM,
                    categoryId = 1,
                    status = TaskStatus.DONE,
                    assignedDate = LocalDate(2023, 12, 15)
                )
            )
            taskService.createTask(
                TaskCreationRequest(
                    title = "First Task",
                    description = "Finish the initial UI design",
                    priority = TaskPriority.LOW,
                    categoryId = 1,
                    status = TaskStatus.TODO,
                    assignedDate = LocalDate(2023, 12, 12)
                )
            )

            taskService.createTask(
                TaskCreationRequest(
                    title = "Second Task",
                    description = "Set up authentication module",
                    priority = TaskPriority.MEDIUM,
                    categoryId = 1,
                    status = TaskStatus.IN_PROGRESS,
                    assignedDate = LocalDate(2023, 12, 13)
                )
            )

            taskService.createTask(
                TaskCreationRequest(
                    title = "Third Task",
                    description = "Write unit tests for login",
                    priority = TaskPriority.HIGH,
                    categoryId = 1,
                    status = TaskStatus.TODO,
                    assignedDate = LocalDate(2023, 12, 14)
                )
            )

            taskService.createTask(
                TaskCreationRequest(
                    title = "Fourth Task",
                    description = "Deploy to staging environment",
                    priority = TaskPriority.MEDIUM,
                    categoryId = 1,
                    status = TaskStatus.DONE,
                    assignedDate = LocalDate(2023, 12, 15)
                )
            )
            _taskScreenUiState.update { it.copy(isLoading = true) }
            //getTasksByStatus(TaskStatus.TODO)
            _taskScreenUiState.update { it.copy(isLoading = false) }
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

    override fun onDeleteIconClicked(idOfTaskToBeDeleted: Long) {
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
            taskService.deleteTask(_taskScreenUiState.value.idOfTaskToBeDeleted.toLong())
            _taskScreenUiState.update { state ->
                state.copy(
                    isBottomSheetVisible = false,
                    deleteBottomSheetUiState = state.deleteBottomSheetUiState.copy(
                        deleteButtonState = ButtonState.IDLE
                    )
                )
            }
            showSnackBar()
        }
    }

    override fun onCancelButtonClicked() {
        _taskScreenUiState.update {
            it.copy(isBottomSheetVisible = false)
        }
    }

    override fun onBottomSheetDismissed() {
        _taskScreenUiState.update {
            viewModelScope.launch {
                delay(400)
            }
            it.copy(isBottomSheetVisible = false)
        }
    }

    override fun onTabSelected(tabIndex: Int) {
        val statusUiState = TaskStatusUiState.entries[tabIndex]
        val status = statusUiState.toDomain()

        viewModelScope.launch {
            taskService.getTasksByStatus(status).collect { tasks ->
                val mappedTasks = tasks.map {
                    val icon = getCategoryIconById(it.categoryId)
                    it.taskToTaskUiState(icon.toInt())
                }

                _taskScreenUiState.update { taskScreenUiState ->
                    taskScreenUiState.copy(
                        selectedTabIndex = tabIndex,
                        listOfTasksUiState = mappedTasks,
                        listOfTabBarItem = taskScreenUiState.listOfTabBarItem.mapIndexed { index, tabItem ->
                            tabItem.copy(
                                isSelected = index == tabIndex,
                                taskCount = mappedTasks.count {
                                    it.status == statusUiState.status
                                }.toString()
                            )
                        }
                    )
                }
            }
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
                datePickerUiState = it.datePickerUiState.copy(
                    isVisible = false,
                    selectedDate = date
                )
            )
        }

    }

    fun showSnackBar() {
        _taskScreenUiState.update {
            it.copy(isSnackBarVisible = true)
        }
    }

    fun hideSnackBar() {
        _taskScreenUiState.update {
            it.copy(isSnackBarVisible = false)
        }
    }

    private suspend fun getTasksByStatus(status: TaskStatus) {
        taskService.getTasksByStatus(status).collect {
            val result = it.map {
                val categoryIcon = getCategoryIconById(it.categoryId)
                it.taskToTaskUiState(categoryIcon.toInt())
            }
            _taskScreenUiState.update { it.copy(listOfTasksUiState = result) }
        }
    }

    private suspend fun getCategoryIconById(categoryId: Long): String {
        return categoryService.getCategoryIconById(categoryId)
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
    val datePickerUiState: DatePickerUiState = DatePickerUiState(),
    val deleteBottomSheetUiState: DeleteBottomSheetUiState = DeleteBottomSheetUiState(),
    val idOfTaskToBeDeleted: Long=0,
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

    @StringRes
    val priority: Int,
    @StringRes
    val status: Int,
    val categoryTitle: String,
    @DrawableRes
    val categoryIcon: Int
)

data class DateCardUiState(
    val dayNumber: String,
    val dayName: String,
    val isSelected: Boolean
)


fun Task.taskToTaskUiState(categoryIcon: Int): TaskUiState {
    return TaskUiState(
        id = id,
        title = title,
        description = description,
        priority = priority.toUiState().priority,
        status = status.toUiState().status,
        categoryTitle = "",
        categoryIcon = categoryIcon
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
        title = TaskStatusUiState.IN_PROGRESS.status, taskCount = "0", isSelected = true
    ),
    TabBarItem(
        title = TaskStatusUiState.TODO.status, taskCount = "0", isSelected = false
    ),
    TabBarItem(
        title = TaskStatusUiState.DONE.status, taskCount = "0", isSelected = false
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


enum class TaskPriorityUiState(@StringRes var priority: Int) {
    LOW(R.string.low),
    MEDIUM(R.string.medium),
    HIGH(R.string.high)
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


