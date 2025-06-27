package com.example.tudee.presentation.screen.task_screen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.data.preferences.PreferencesManager
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.TaskStatus
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
    private val savedStateHandle: SavedStateHandle,
    private val themePrefs: PreferencesManager
) : ViewModel(), TaskScreenInteractor {
    private val _taskScreenUiState = MutableStateFlow(TasksScreenUiState( isDarkMode = themePrefs.isDarkMode()))
    val taskScreenUiState = _taskScreenUiState

    private val args: Int = checkNotNull(savedStateHandle["status"])
    private val _triggerEffectVersion = MutableStateFlow(0)
    val triggerEffectVersion: StateFlow<Int> = _triggerEffectVersion

    init {
        initStatus(args)
        viewModelScope.launch {
            _taskScreenUiState.update { it.copy(isLoading = true) }
            getTasksByStatusAndDate(
                status = TaskStatus.entries[_taskScreenUiState.value.selectedTabIndex],
                date = getSelectedDate(),
                tabIndex = _taskScreenUiState.value.selectedTabIndex
            )
            _taskScreenUiState.update { it.copy(isLoading = false) }
        }
    }

    private fun initStatus(status: Int) {
        _taskScreenUiState.update { it.copy(selectedTabIndex = status) }

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
        val statusUiState = TaskStatusUiState.entries[_taskScreenUiState.value.selectedTabIndex]
        val status = statusUiState.toDomain()
        getTasksByStatusAndDate(
            status = status,
            date = getSelectedDate(),
            tabIndex = _taskScreenUiState.value.selectedTabIndex
        )
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
        _taskScreenUiState.update { it.copy(selectedTabIndex = tabIndex) }
        val statusUiState = TaskStatusUiState.entries[tabIndex]
        val status = statusUiState.toDomain()
        getTasksByStatusAndDate(status = status, tabIndex = tabIndex, date = getSelectedDate())
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
                    priority = taskUiState.priority,
                    status = taskUiState.status
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
                        selectedYearMonth = selectedYearMonth,
                    )
                )
            }
            onDayCardClicked(localPickedDate.dayOfMonth - 1)
            _triggerEffectVersion.update { it + 1 }
        }
        val statusUiState = TaskStatusUiState.entries[_taskScreenUiState.value.selectedTabIndex]
        val date = getSelectedDate()

        val status = statusUiState.toDomain()
        getTasksByStatusAndDate(
            status = status,
            tabIndex = _taskScreenUiState.value.selectedTabIndex,
            date = date
        )
    }

    fun onPreviousArrowClicked() {
        val prevMonth = _taskScreenUiState.value.dateUiState.selectedYearMonth.minusMonths(1)
        updateDaysInMonth(prevMonth)
    }

    fun onNextArrowClicked() {
        val nextMonth = _taskScreenUiState.value.dateUiState.selectedYearMonth.plusMonths(1)
        updateDaysInMonth(nextMonth)
    }

    fun hideDetailsBottomSheet() {
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

    private fun getTasksByStatusAndDate(status: TaskStatus, date: String, tabIndex: Int = 0) {
        job?.cancel()
        job = viewModelScope.launch {
            taskService.getTasksByStatusAndDate(status, date).collect {
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

        _taskScreenUiState.update { currentState ->
            currentState.copy(
                dateUiState = currentState.dateUiState.copy(
                    selectedYearMonth = YearMonth.of(
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

        val totalDays = month.lengthOfMonth()

        val fallbackDate = selectedDate ?: run {
            val selectedDayNumber =
                _taskScreenUiState.value.dateUiState.daysCardsData.firstOrNull { it.isSelected }?.dayNumber
            val safeDay = selectedDayNumber?.coerceAtMost(totalDays) ?: 1
            month.atDay(safeDay)
        }

        return (1..totalDays).map { day ->
            val date = month.atDay(day)
            DateCardUiState(
                dayNumber = date.dayOfMonth,
                dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                isSelected = date == fallbackDate,
            )
        }
    }

    private fun getSelectedDate(): String {
        val dateUiState = _taskScreenUiState.value.dateUiState
        val selectedDay = dateUiState.daysCardsData.find { it.isSelected }?.dayNumber
        return selectedDay?.let {
            LocalDate.of(
                dateUiState.selectedYearMonth.year,
                dateUiState.selectedYearMonth.month,
                it
            )
                .toString()
        } ?: LocalDate.now().toString()
    }
}



