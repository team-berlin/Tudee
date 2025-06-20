package com.example.tudee.presentation.screen.task_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.TabBarItem
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.screen.task_screen.interactors.TaskScreenInteractor
import com.example.tudee.presentation.screen.task_screen.mappers.TaskStatusUiState
import com.example.tudee.presentation.screen.task_screen.mappers.taskToTaskUiState
import com.example.tudee.presentation.screen.task_screen.mappers.toDomain
import com.example.tudee.presentation.screen.task_screen.ui_states.DateCardUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TasksScreenUiState
import com.example.tudee.presentation.viewmodel.taskuistate.TaskDetailsUiState
import kotlinx.coroutines.Job
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

            _taskScreenUiState.update { it.copy(isLoading = true) }
            getTasksByStatus(TaskStatus.IN_PROGRESS)
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

    override fun onConfirmDelete() {
        _taskScreenUiState.update { state ->
            state.copy(
                deleteBottomSheetUiState = state.deleteBottomSheetUiState.copy(
                    deleteButtonState = ButtonState.LOADING
                )
            )
        }
        viewModelScope.launch {
            taskService.deleteTask(_taskScreenUiState.value.idOfTaskToBeDeleted)
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

    var job: Job? = null
    override fun onTabSelected(tabIndex: Int) {
        val statusUiState = TaskStatusUiState.entries[tabIndex]
        val status = statusUiState.toDomain()
        getTasksByStatus(status = status, tabIndex = tabIndex)

    }

    override fun onFloatingActionClicked() {
        _taskScreenUiState.update { it.copy(isAddBottomSheetVisible = true) }
    }

    override fun onTaskCardClicked(taskUiState: TaskUiState) {
        _taskScreenUiState.update {
            it.copy(
                taskDetailsUiState = TaskDetailsUiState(
                    id = taskUiState.id,
                    title = taskUiState.title,
                    description = taskUiState.description,
                    categoryIconRes = taskUiState.categoryIcon.toInt(),
                    priority = TaskPriority.LOW,
                    status = TaskStatus.IN_PROGRESS
                )
            )

        }
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

    private suspend fun getCategoryIconById(categoryId: Long): String {
        return categoryService.getCategoryIconById(categoryId)
    }

    private fun getTasksByStatus(status: TaskStatus, tabIndex: Int = 0) {
        job?.cancel()
        job = viewModelScope.launch {
            taskService.getTasksByStatus(status).collect {
                val result = it.map { task ->
                    val categoryIcon = getCategoryIconById(task.categoryId)
                    task.taskToTaskUiState(categoryIcon.toInt())
                }

                _taskScreenUiState.update { uiState ->
                    uiState.copy(
                        selectedTabIndex = tabIndex,
                        listOfTasksUiState = result,
                        listOfTabBarItem = taskScreenUiState.value.listOfTabBarItem.mapIndexed { index, tabItem ->
                            if (index == tabIndex) {
                                tabItem.copy(
                                    isSelected = true,
                                    taskCount = result.size.toString()
                                )
                            } else {
                                tabItem.copy(isSelected = false)
                            }
                        }
                    )
                }
            }
        }
    }
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

