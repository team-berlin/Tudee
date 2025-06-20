package com.example.tudee.presentation.screen.category.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.presentation.screen.category.model.CategoryData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CategoryTasksViewModel(
    private val taskService: TaskService,
    private val taskCategoryService: TaskCategoryService
) : ViewModel() {

    private val _categoryTasksUiState =
        MutableStateFlow(CategoryTasksUiState(loading = false))
    val categoryTasksUiState: StateFlow<CategoryTasksUiState> get() = _categoryTasksUiState

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    fun getTasksByCategoryId(categoryId: Long) {
        _categoryTasksUiState.value = _categoryTasksUiState.value.copy(loading = true)

        viewModelScope.launch {
            taskService.getTasksByCategoryId(categoryId)
                .map { tasks ->
                    groupTasksByCategory(tasks)
                }
                .collect { categoryTasks ->
                    _categoryTasksUiState.value =
                        _categoryTasksUiState.value.copy(
                            loading = false,
                            categoryTasksUiModel = categoryTasks.first()
                        )
                }
        }
    }

    fun deleteCategory() {
        _categoryTasksUiState.value.categoryTasksUiModel?.let {
            viewModelScope.launch {
                taskCategoryService.deleteCategory(it.id)
            }
        }
    }

    fun editCategory(category: CategoryData) {
        _categoryTasksUiState.value.categoryTasksUiModel?.let {
            viewModelScope.launch {
                taskCategoryService.editCategory(
                    TaskCategory(
                        id = it.id,
                        title = category.name,
                        image = category.uiImage.asString(),
                        isPredefined = it.isPredefined
                    )
                )
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
