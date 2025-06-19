package com.example.tudee.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.presentation.viewmodel.uistate.TaskBottomSheetState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class AddTaskBottomSheetViewModel(
    private val taskService: TaskService,
    private val categoryService: TaskCategoryService
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskBottomSheetState> = MutableStateFlow(TaskBottomSheetState())
    val uiState: StateFlow<TaskBottomSheetState> = _uiState.asStateFlow()

    val isTaskValid: StateFlow<Boolean> = _uiState
        .map { state ->
            state.taskTitle.isNotBlank() &&
                    state.taskDescription.isNotBlank() &&
                    state.selectedTaskPriority != null &&
                    state.selectedCategoryId != null
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun onUpdateTaskTitle(newTitle: String) {
        _uiState.update { it.copy(taskTitle = newTitle) }
    }

    fun onUpdateTaskDescription(newDescription: String) {
        _uiState.update { it.copy(taskDescription = newDescription) }
    }

    fun onUpdateTaskDueDate(newDueDate: LocalDate) {
        _uiState.update { it.copy(taskDueDate = newDueDate) }
    }

    fun onSelectTaskPriority(newPriority: TaskPriority) {
        _uiState.update { it.copy(selectedTaskPriority = newPriority) }
    }

    fun onSelectTaskCategory(selectedCategoryId: Long) {
        _uiState.update { it.copy(selectedCategoryId = selectedCategoryId) }
    }

    fun showButtonSheet() {
        _uiState.update { it.copy(isButtonSheetVisible = true) }
    }

    fun hideButtonSheet() {
        _uiState.update {
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
                    _uiState.update {
                        it.copy(
                            isEditMode = true,
                            taskId = taskId,
                            taskStatus = status,
                            taskTitle = title,
                            taskDescription = description,
                            selectedTaskPriority = priority,
                            selectedCategoryId = categoryId,
                            taskDueDate = assignedDate
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error fetching task: ${e.message}")
            }
        }
    }

    fun onAddNewTaskClicked(taskCreationRequest: TaskCreationRequest) {
        viewModelScope.launch {
            try {
                taskService.createTask(taskCreationRequest)
                _uiState.update { it.copy(snackBarMessage = true) }
                delay(2000)
                hideButtonSheet()
            } catch (e: Exception) {
                _uiState.update { it.copy(snackBarMessage = false) }
                delay(2000)
                hideButtonSheet()
            }
        }
    }

    fun onSaveClicked(editedTask: Task) {
        viewModelScope.launch {
            try {
                taskService.editTask(editedTask)
                _uiState.update { it.copy(snackBarMessage = true) }
                delay(2000)
                hideButtonSheet()
            } catch (e: Exception) {
                _uiState.update { it.copy(snackBarMessage = false) }
                delay(2000)
                hideButtonSheet()
            }
        }
    }

    fun onCancelClicked() {
        hideButtonSheet()
    }
}