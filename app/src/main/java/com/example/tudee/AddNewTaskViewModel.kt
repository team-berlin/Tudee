package com.example.tudee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.request.TaskCreationRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDateTime

class AddNewTaskViewModel(
//    taskService: TaskService,
) : ViewModel() {

    private val _taskState: MutableStateFlow<AddNewTaskScreenState> =
        MutableStateFlow(AddNewTaskScreenState())
    val taskState: StateFlow<AddNewTaskScreenState> = _taskState.asStateFlow()

    val isTaskValid: StateFlow<Boolean> = _taskState
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
        _taskState.value = _taskState.value.copy(
            taskTitle = newTitle,
        )
    }

    fun onUpdateTaskDescription(newDescription: String) {
        _taskState.value = _taskState.value.copy(
            taskDescription = newDescription,
        )
    }

    fun onUpdateTaskDueDate(newDueDate: LocalDateTime) {
        _taskState.value = _taskState.value.copy(
            taskDueDate = newDueDate,
        )
    }

    fun onUpdateTaskPriority(newPriority: TaskPriority) {
        _taskState.value = _taskState.value.copy(
            selectedTaskPriority = newPriority
        )
    }

    fun onSelectTaskCategory(selectedCategoryID: Long) {
        _taskState.value = _taskState.value.copy(
            selectedCategoryId = selectedCategoryID
        )
    }

    fun onAddClicked(taskCreationRequest: TaskCreationRequest) {

    }

    fun onCancelClicked() {

    }


}