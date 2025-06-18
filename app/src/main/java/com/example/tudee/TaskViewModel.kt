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
    private val categoryService: TaskCategoryService,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskScreenState> =
        MutableStateFlow(TaskScreenState())
    val uiState: StateFlow<TaskScreenState> = _uiState.asStateFlow()

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    private val _taskId = MutableStateFlow<Long?>(null)
    val taskId: StateFlow<Long?> = _taskId.asStateFlow()

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
        Log.d("TaskViewModel", "Initializing TaskViewModel at 07:12 PM EEST, June 18, 2025")
        observeCategories()
    }

    private fun observeCategories() {
        viewModelScope.launch {
            Log.d("TaskViewModel", "Observing categories at 07:12 PM EEST, June 18, 2025")
            categoryService.getCategories()
                .collect { list ->
                    Log.d("TaskViewModel", "Categories updated: $list at 07:12 PM EEST, June 18, 2025")
                    _uiState.update { it.copy(categories = list) }
                }
        }
    }

    fun onUpdateTaskTitle(newTitle: String) {
        Log.d("TaskViewModel", "Updating task title to: $newTitle at 07:12 PM EEST, June 18, 2025")
        _uiState.update { it.copy(taskTitle = newTitle) }
    }

    fun onUpdateTaskDescription(newDescription: String) {
        Log.d("TaskViewModel", "Updating task description to: $newDescription at 07:12 PM EEST, June 18, 2025")
        _uiState.update { it.copy(taskDescription = newDescription) }
    }

    fun onUpdateTaskDueDate(newDueDate: LocalDate) {
        Log.d("TaskViewModel", "Updating task due date to: $newDueDate at 07:12 PM EEST, June 18, 2025")
        _uiState.update { it.copy(taskDueDate = newDueDate) }
    }

    fun onUpdateTaskPriority(newPriority: TaskPriority) {
        Log.d("TaskViewModel", "Updating task priority to: $newPriority at 07:12 PM EEST, June 18, 2025")
        _uiState.update { it.copy(selectedTaskPriority = newPriority) }
    }

    fun onSelectTaskCategory(selectedCategoryID: Long) {
        Log.d("TaskViewModel", "Selecting category ID: $selectedCategoryID at 07:12 PM EEST, June 18, 2025")
        _uiState.update { it.copy(selectedCategoryId = selectedCategoryID) }
    }

    fun setEditMode(isEdit: Boolean, taskId: Long? = null) {
        Log.d("TaskViewModel", "Setting edit mode to: $isEdit, taskId: $taskId at 07:12 PM EEST, June 18, 2025")
        _isEditMode.value = isEdit
        _taskId.value = taskId
        _uiState.update { it.copy(showBottomSheet = true) } // Show sheet only when button is clicked
        if (isEdit && taskId != null) {
            Log.d("TaskViewModel", "Fetching task with ID: $taskId at 07:12 PM EEST, June 18, 2025")
            viewModelScope.launch {
                try {
                    val task = taskService.getTaskById(taskId)
                    Log.d("TaskViewModel", "Task fetched: $task at 07:12 PM EEST, June 18, 2025")
                    _uiState.update {
                        it.copy(
                            taskTitle = task.title,
                            taskDescription = task.description,
                            selectedTaskPriority = task.priority,
                            selectedCategoryId = task.categoryId,
                            taskDueDate = task.assignedDate
                        )
                    }
                } catch (e: Exception) {
                    Log.e("TaskViewModel", "Error fetching task: ${e.message} at 07:12 PM EEST, June 18, 2025")
                }
            }
        } else {
            // Reset to default values for Add case
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
        Log.d("TaskViewModel", "Add/Save clicked, isEditMode: ${_isEditMode.value}, taskId: ${_taskId.value} at 07:12 PM EEST, June 18, 2025")
        viewModelScope.launch {
            try {
                if (_isEditMode.value && _taskId.value != null) {
                    val task = _uiState.value.run {
                        Task(
                            id = _taskId.value!!,
                            title = taskTitle,
                            description = taskDescription,
                            priority = selectedTaskPriority!!,
                            categoryId = selectedCategoryId!!,
                            status = TaskStatus.TODO,
                            assignedDate = taskDueDate ?: LocalDate(2024, 1, 1)
                        )
                    }
                    Log.d("TaskViewModel", "Editing task: $task at 07:12 PM EEST, June 18, 2025")
                    taskService.editTask(task)
                    Log.d("TaskViewModel", "Task edited successfully at 07:12 PM EEST, June 18, 2025")
                } else {
                    Log.d("TaskViewModel", "Creating task: $taskCreationRequest at 07:12 PM EEST, June 18, 2025")
                    taskService.createTask(taskCreationRequest)
                    Log.d("TaskViewModel", "Task created successfully at 07:12 PM EEST, June 18, 2025")
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error saving task: ${e.message} at 07:12 PM EEST, June 18, 2025")
            }
        }
    }

    fun onDismissBottomSheet() {
        Log.d("TaskViewModel", "Dismissing bottom sheet at 07:12 PM EEST, June 18, 2025")
        _uiState.update { it.copy(showBottomSheet = false) }
        _isEditMode.value = false
        _taskId.value = null
    }
}