package com.example.tudee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
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
    private val categoryService: TaskCategoryService
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskScreenState> =
        MutableStateFlow(TaskScreenState())
    val uiState: StateFlow<TaskScreenState> = _uiState.asStateFlow()

    private val _isEditMode = MutableStateFlow(false)
  //  val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    private val _taskId = MutableStateFlow<Long?>(null)

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
        Log.d("TaskViewModel", "Updating task title to: $newTitle")
        _uiState.update { it.copy(taskTitle = newTitle) }
    }

    fun onUpdateTaskDescription(newDescription: String) {
        _uiState.update { it.copy(taskDescription = newDescription) }
    }

    fun onUpdateTaskDueDate(newDueDate: LocalDate) {
        _uiState.update { it.copy(taskDueDate = newDueDate) }
    }

    fun onUpdateTaskPriority(newPriority: TaskPriority) {
        Log.d("TaskViewModel", "Updating task priority to: $newPriority")
        _uiState.update { it.copy(selectedTaskPriority = newPriority) }
    }

    fun onSelectTaskCategory(selectedCategoryID: Long) {
        _uiState.update { it.copy(selectedCategoryId = selectedCategoryID) }
    }

    fun onShowBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = true) }
    }

    fun setEditMode(isEdit: Boolean, taskId: Long? = null) {
        _isEditMode.value = isEdit
        _taskId.value = taskId
        if (isEdit && taskId != null) {
            viewModelScope.launch {
                val task = taskService.getTaskById(taskId)
                _uiState.update {
                    it.copy(
                        taskTitle = task.title,
                        taskDescription = task.description,
                        selectedTaskPriority = task.priority,
                        selectedCategoryId = task.categoryId,
                        taskDueDate = task.assignedDate
                    )
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    taskTitle = "",
                    taskDescription = "",
                    selectedTaskPriority = null,
                    selectedCategoryId = null,
                    taskDueDate = null
                )
            }
        }
    }

    fun onAddOrSaveClicked(taskCreationRequest: TaskCreationRequest) {
        viewModelScope.launch {
            if (_isEditMode.value && _taskId.value != null) {
                val task = _uiState.value.run {
                    Task(
                        id = _taskId.value!!,
                        title = taskTitle,
                        description = taskDescription,
                        priority = selectedTaskPriority ?: TaskPriority.MEDIUM,
                        categoryId = selectedCategoryId ?: 1L,
                        status = TaskStatus.TODO,
                        assignedDate = taskDueDate ?: LocalDate(2024, 1, 1)
                    )
                }
                taskService.editTask(task)
            } else {
                taskService.createTask(taskCreationRequest)
            }
        }
    }

    fun onDismissBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = false) }
        _isEditMode.value = false
        _taskId.value = null
    }
}