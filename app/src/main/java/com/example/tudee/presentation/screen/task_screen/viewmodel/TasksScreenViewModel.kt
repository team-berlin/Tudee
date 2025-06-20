package com.example.tudee.presentation.screen.task_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.TabBarItem
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.screen.task_screen.interactors.TaskScreenInteractor
import com.example.tudee.presentation.screen.task_screen.mappers.TaskStatusUiState
import com.example.tudee.presentation.screen.task_screen.mappers.taskToTaskUiState
import com.example.tudee.presentation.screen.task_screen.mappers.toDomain
import com.example.tudee.presentation.screen.task_screen.ui_states.DateCardUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskDetailsUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TasksScreenUiState
import com.example.tudee.utils.convertMillisToLocalDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class TasksScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: TaskCategoryService,
) : ViewModel(), TaskScreenInteractor {
    private val _taskScreenUiState = MutableStateFlow(TasksScreenUiState())
    val taskScreenUiState = _taskScreenUiState

    private val _triggerEffectVersion = MutableStateFlow(0)
    val triggerEffectVersion: StateFlow<Int> = _triggerEffectVersion

    init {

        viewModelScope.launch {
            _taskScreenUiState.update { it.copy(isLoading = true) }
            getTasksByStatus(TaskStatus.TODO)
            _taskScreenUiState.update { it.copy(isLoading = false) }
        }

        updateDaysInMonth(
            month = YearMonth.now(), selectedDate = LocalDate.now()
        )
    }


    fun updateStatus(status: Int) {
        _taskScreenUiState.update { it.copy(selectedTabIndex = status) }
    }

    override fun onDayCardClicked(cardIndex: Int) {
        _taskScreenUiState.update {
            it.copy(
                dateUiState = it.dateUiState.copy(
                    daysCardsData = it.dateUiState.daysCardsData.mapIndexed { i, card ->
                        card.copy(isSelected = cardIndex == i)
                    },
                )
            )
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
                    categoryIconRes = taskUiState.categoryIcon,
                    priority = TaskPriority.LOW,
                    status = TaskStatus.IN_PROGRESS
                )
            )

        }
    }

    override fun onCalendarClicked() {
        _taskScreenUiState.update {
                it.copy(
                    dateUiState = it.dateUiState.copy(
                        isDatePickerVisible = true
                    )
                )
            }
    }

    override fun onDismissDatePicker() {
        _taskScreenUiState.update {
            it.copy(
                dateUiState = it.dateUiState.copy(isDatePickerVisible = false)
            )
        }
    }

    fun onConfirmDatePicker(millis: Long?) {
        millis?.let {
            val localPickedDate = convertMillisToLocalDate(millis)

            val selectedYearMonth = YearMonth.of(
                localPickedDate.year, localPickedDate.month
            )

            _taskScreenUiState.update {
                it.copy(
                    dateUiState = it.dateUiState.copy(
                        selectedYear = localPickedDate.year.toString(),
                        selectedMonth = selectedYearMonth,
                    )
                )
            }
            onDayCardClicked(localPickedDate.dayOfMonth - 1)
            _triggerEffectVersion.update { it + 1 }
        }
    }

    fun onPreviousArrowClicked() {
        val prevMonth = _taskScreenUiState.value.dateUiState.selectedMonth.minusMonths(1)
        updateDaysInMonth(prevMonth)
    }

    fun onNextArrowClicked() {
        val nextMonth = _taskScreenUiState.value.dateUiState.selectedMonth.plusMonths(1)
        updateDaysInMonth(nextMonth)
    }

    fun hideDetialsBottomSheet() {
        _taskScreenUiState.update {
            it.copy(
                taskDetailsUiState = null
            )
        }
    }

    var job: Job? = null

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

    suspend fun getCategoryIconById(categoryId: Long): String {
        return categoryService.getCategoryIconById(categoryId)
    }

    private fun getTasksByStatus(status: TaskStatus, tabIndex: Int = 0) {
        job?.cancel()
        job = viewModelScope.launch {
            taskService.getTasksByStatus(status).collect {
                val result = it.map { task ->
                    val categoryIcon = getCategoryIconById(task.categoryId)
                    task.taskToTaskUiState(categoryIcon)
                }
                _taskScreenUiState.update { uiState ->
                    uiState.copy(
                        selectedTabIndex = tabIndex,
                        listOfTasksUiState = result,
                        listOfTabBarItem = taskScreenUiState.value.listOfTabBarItem.mapIndexed { index, tabItem ->
                            if (index == tabIndex) {
                                tabItem.copy(
                                    isSelected = true, taskCount = result.size.toString()
                                )
                            } else {
                                tabItem.copy(isSelected = false)
                            }
                        })
                }
            }
        }
    }

    private fun updateDaysInMonth(month: YearMonth, selectedDate: LocalDate? = null) {
        val days = getDaysInMonth(month, selectedDate = selectedDate)

        val oldDateUiState = _taskScreenUiState.value.dateUiState

        _taskScreenUiState.update { state ->
            state.copy(
                dateUiState = oldDateUiState.copy(
                    selectedMonth = YearMonth.of(
                        month.year, month.month
                    ),
                    selectedYear = month.year.toString(),
                    daysCardsData = days,
                )
            )
        }
    }

    private fun getDaysInMonth(
        month: YearMonth, selectedDate: LocalDate?

    ): List<DateCardUiState> {
        val fallbackDate = selectedDate ?: month.atDay(1)
        val totalDays = month.lengthOfMonth()
        return (1..totalDays).map { day ->
            val date = month.atDay(day)
            DateCardUiState(
                dayNumber = date.dayOfMonth,
                dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                isSelected = date == fallbackDate
            )
        }
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

