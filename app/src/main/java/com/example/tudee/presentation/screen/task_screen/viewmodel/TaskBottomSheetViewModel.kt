package com.example.tudee.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.data.mapper.toCategoryUiState
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskBottomSheetState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TaskBottomSheetViewModel(
    private val taskService: TaskService,
    private val categoryService: TaskCategoryService
) : ViewModel() {

    private val _taskBottomSheetUiState: MutableStateFlow<TaskBottomSheetState> =
        MutableStateFlow(TaskBottomSheetState())
    val uiState: StateFlow<TaskBottomSheetState> = _taskBottomSheetUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val categoryUiStates =
                categoryService.getCategories().first().map { it.toCategoryUiState() }
            _taskBottomSheetUiState.update { it.copy(categories = categoryUiStates) }
            }
        }


    val isTaskValid: StateFlow<Boolean> = _taskBottomSheetUiState
        .map { state ->
            state.taskTitle.isNotBlank() &&
                    state.taskDescription.isNotBlank() &&
                    state.selectedTaskPriority != null &&
                    state.selectedCategoryId != null
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = false
        )

    fun onUpdateTaskTitle(newTitle: String) {
        _taskBottomSheetUiState.update { it.copy(taskTitle = newTitle) }
    }

    fun onUpdateTaskDescription(newDescription: String) {
        _taskBottomSheetUiState.update { it.copy(taskDescription = newDescription) }
    }

    fun onUpdateTaskDueDate(newDueDate: LocalDate) {
        _taskBottomSheetUiState.update { it.copy(taskDueDate = newDueDate.toString()) }
    }

    fun onSelectTaskPriority(newPriority: TaskPriority) {
        _taskBottomSheetUiState.update { it.copy(selectedTaskPriority = newPriority) }
    }

    fun onSelectTaskCategory(selectedCategoryId: Long) {
        _taskBottomSheetUiState.update { it.copy(selectedCategoryId = selectedCategoryId) }
    }

    fun showButtonSheet() {
        _taskBottomSheetUiState.update { it.copy(isButtonSheetVisible = true) }
    }

    fun hideButtonSheet() {
        _taskBottomSheetUiState.update {
            it.copy(
                isButtonSheetVisible = false,
                isEditMode = false,
                taskTitle = "",
                taskDescription = "",
                selectedTaskPriority = null,
                selectedCategoryId = null,
                taskDueDate = null,
                snackBarMessage = null
            )
        }
    }

    fun getTaskInfoById(taskId: Long) {
        viewModelScope.launch {
            try {
                with(taskService.getTaskById(taskId)) {
                    _taskBottomSheetUiState.update {
                        it.copy(
                            isEditMode = true,
                            taskId = taskId,
                            taskStatus = status,
                            taskTitle = title,
                            taskDescription = description,
                            selectedTaskPriority = priority,
                            selectedCategoryId = categoryId,
                            taskDueDate = assignedDate.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error  ${e.message}")
            }
        }
    }

    fun onAddNewTaskClicked(taskCreationRequest: TaskCreationRequest) {
        viewModelScope.launch {
            try {
                taskService.createTask(taskCreationRequest)
                _taskBottomSheetUiState.update { it.copy(snackBarMessage = true) }
                delay(2000)
                hideButtonSheet()
            } catch (e: Exception) {
                _taskBottomSheetUiState.update { it.copy(snackBarMessage = false) }
                delay(2000)
                hideButtonSheet()
            }
        }
    }

    fun onSaveClicked(editedTask: Task) {
        viewModelScope.launch {
            try {
                taskService.editTask(editedTask)
                _taskBottomSheetUiState.update { it.copy(snackBarMessage = true) }
                delay(2000)
                hideButtonSheet()
            } catch (e: Exception) {
                _taskBottomSheetUiState.update { it.copy(snackBarMessage = false) }
                delay(2000)
                hideButtonSheet()
            }
        }
    }
    fun onDateFieldClicked(){
        _taskBottomSheetUiState.update { it.copy(isDatePickerVisible = true) }
    }
    fun onDismissDatePicker() {
        _taskBottomSheetUiState.update { it.copy(isDatePickerVisible = false)

        }
    }

    fun onConfirmDatePicker(millis: Long?) {
        millis?.let {
            val instant = Instant.Companion.fromEpochMilliseconds(millis)
            val timeZone = TimeZone.Companion.currentSystemDefault() // or a fixed one
            val localDateTime = instant.toLocalDateTime(timeZone)
             // returns kotlinx.datetime.LocalDate
            val selectedDate = LocalDate(
                year = localDateTime.date.year,
                monthNumber = localDateTime.date.monthNumber,
                dayOfMonth = localDateTime.date.dayOfMonth
            )
            _taskBottomSheetUiState.update {
                it.copy(
                    taskDueDate = selectedDate.toString())
            }

        }
    }

    fun onCancelClicked() {
        hideButtonSheet()
    }
    fun updateTaskStatusToDone(taskId: Long) {
        viewModelScope.launch {
            try {
                val task = taskService.getTaskById(taskId)
                val updatedTask = task.copy(status = TaskStatus.DONE)
                taskService.editTask(updatedTask)
                _taskBottomSheetUiState.update { it.copy(snackBarMessage = true) }
                delay(2000)
                hideButtonSheet()
            } catch (e: Exception) {
                _taskBottomSheetUiState.update { it.copy(snackBarMessage = false) }
            }
        }
    }
}