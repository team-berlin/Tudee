package com.example.tudee.presentation.screen.manipulateTask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.request.TaskCreationRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TaskViewModel(
    private val taskService: TaskService,
    private val categoryService: TaskCategoryService,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskBottomSheetState> =
        MutableStateFlow(TaskBottomSheetState())
    val uiState: StateFlow<TaskBottomSheetState> = _uiState.asStateFlow()

    val isTaskValid: StateFlow<Boolean> = _uiState
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

    init {
//        observeCategories()
    }

    private fun observeCategories() {
        viewModelScope.launch {
            categoryService.getCategories()
                .collect { list ->
                    _uiState.update { it.copy(categories = list) }
                }
        }
    }

    fun onUpdateTaskTitle(newTitle: String) {
        Log.d("TaskViewModel", "Updating task title to: $newTitle")
        _uiState.update { it.copy(taskTitle = newTitle) }
    }

    fun onUpdateTaskDescription(newDescription: String) {
        _uiState.update { it.copy(taskDescription = newDescription) }
    }

    fun onUpdateTaskDueDate(newDueDate: LocalDate) {
        _uiState.update { it.copy(taskDueDate = newDueDate) }
    }

    fun onSelectTaskPriority(newPriority: TaskPriority) {
        Log.d("TaskViewModel", "Updating task priority to: $newPriority")
        _uiState.update { it.copy(selectedTaskPriority = newPriority) }
    }

    fun onSelectTaskCategory(selectedCategoryID: Long) {
        _uiState.update { it.copy(selectedCategoryId = selectedCategoryID) }
    }

    fun showButtonSheet() {
        _uiState.update { it.copy(isButtonSheetVisible = true) }
    }

    fun getTaskInfoById(taskId: Long) {
        viewModelScope.launch {
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
                        taskDueDate = assignedDate,
                    )
                }
            }
        }
    }

    fun onAddNewTaskClicked(taskCreationRequest: TaskCreationRequest) {
        viewModelScope.launch {
            taskService.createTask(taskCreationRequest)
            Log.d("TaskViewModel", "Task created: $taskCreationRequest")
        }
//        _uiState.update { it.copy(showBottomSheet = true, isEditMode = false) }
    }

    fun onSaveClicked(editedTask: Task) {
        viewModelScope.launch {
            taskService.editTask(editedTask)
            hideButtonSheet()
            Log.d("TaskViewModel", "Task saved: $editedTask")
        }
    }

    fun onCancelClicked() {
        hideButtonSheet()
        Log.d("TaskViewModel", "Edit cancelled")
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
                taskDueDate = null
            )
        }
    }
}