package com.example.tudee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
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

class AddNewTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: TaskCategoryService,
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddNewTaskScreenState> =
        MutableStateFlow(AddNewTaskScreenState())
    val uiState: StateFlow<AddNewTaskScreenState> = _uiState.asStateFlow()

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

    init {
        observeCategories()
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
        _uiState.update {
            it.copy(
                taskTitle = newTitle,
            )
        }
    }

    fun onUpdateTaskDescription(newDescription: String) {
        _uiState.update {
            it.copy(
                taskDescription = newDescription,
            )
        }
    }

    fun onUpdateTaskDueDate(newDueDate: LocalDate) {
        _uiState.update {
            it.copy(
                taskDueDate = newDueDate,
            )
        }
    }

    fun onUpdateTaskPriority(newPriority: TaskPriority) {
        _uiState.update {
            it.copy(
                selectedTaskPriority = newPriority
            )
        }
    }

    fun onSelectTaskCategory(selectedCategoryID: Long) {
        _uiState.update {
            it.copy(
                selectedCategoryId = selectedCategoryID
            )
        }
    }

    fun onAddClicked(taskCreationRequest: TaskCreationRequest) {
        viewModelScope.launch {
            taskService.createTask(taskCreationRequest)
        }
    }

    fun onDismissBottomSheet() {
        _uiState.update {
            it.copy(
                showBottomSheet = false
            )
        }
    }


}