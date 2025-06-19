package com.example.tudee.presentation.screen.categorytasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CategoryTasksViewModel(
    private val taskService: TaskService,
    private val taskCategoryService: TaskCategoryService
) : ViewModel() {

    private val _categoryTasksUiState =
        MutableStateFlow<CategoryTasksUiState>(CategoryTasksUiState.Loading)
    val categoryTasksUiState: StateFlow<CategoryTasksUiState> get() = _categoryTasksUiState

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    fun showError() {
        viewModelScope.launch {
            _snackBarEvent.emit(SnackBarEvent.ShowError)
        }
    }

    fun getTasksByCategoryId(categoryId: Long) {
        _categoryTasksUiState.value = CategoryTasksUiState.Loading

        viewModelScope.launch {
            taskService.getTasksByCategoryId(categoryId)
                .map { tasks ->
                    groupTasksByCategory(tasks)
                }
                .collect { categoryTasks ->
                    _categoryTasksUiState.value =
                        CategoryTasksUiState.Success(categoryTasks.first())
                }
        }
    }

    private suspend fun groupTasksByCategory(tasks: List<Task>): List<CategoryTasksUiModel> {
        return tasks.groupBy { it.categoryId }
            .mapNotNull { (categoryId, tasksInCategory) ->
                val category = getCategoryById(categoryId)
                category?.let {
                    CategoryTasksUiModel(
                        id = category.id,
                        title = category.title,
                        image = category.image,
                        isPredefined = category.isPredefined,
                        tasks = tasksInCategory.map { it.toTaskUIModel() }
                    )
                }
            }
    }

    private suspend fun getCategoryById(categoryId: Long): TaskCategory? {
        return taskCategoryService.getCategories().first().firstOrNull {
            it.id == categoryId
        }
    }
}
