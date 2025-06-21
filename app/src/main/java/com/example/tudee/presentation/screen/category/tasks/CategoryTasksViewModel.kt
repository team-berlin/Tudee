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

    private val _categoryTasksUiState = MutableStateFlow(CategoryTasksUiState(loading = true))
    val categoryTasksUiState: StateFlow<CategoryTasksUiState> = _categoryTasksUiState

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()


    init {
        viewModelScope.launch {
            try {
                getTasksByCategoryId(1)
                getTasksByStatus(categoryTasksUiState.value, 0)

            } catch (e: Exception) {
                _snackBarEvent.emit(SnackBarEvent.ShowError)
            }
        }
    }

    fun getTasksByCategoryId(categoryId: Long) {
        _categoryTasksUiState.value = _categoryTasksUiState.value.copy(loading = true)

        viewModelScope.launch {
            try {
                taskService.getTasksByCategoryId(categoryId)
                    .map { tasks -> groupTasksByCategory(tasks) }
                    .collect { categoryTasks ->
                        _categoryTasksUiState.value = if (categoryTasks.isNotEmpty()) {
                            _categoryTasksUiState.value.copy(
                                loading = false,
                                categoryTasksUiModel = categoryTasks.first()
                            )
                        } else {
                            _categoryTasksUiState.value.copy(
                                loading = false,
                                categoryTasksUiModel = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _categoryTasksUiState.value = _categoryTasksUiState.value.copy(loading = false)
                _snackBarEvent.emit(SnackBarEvent.ShowError)
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
                    val categoryTasksUiModel = CategoryTasksUiModel(
                        id = category.id,
                        title = category.title,
                        image = category.image,
                        isPredefined = category.isPredefined,
                        tasks = tasksInCategory.map { it.toTaskUIModel() }
                    )
                    categoryTasksUiModel
                }
            }
    }

    private suspend fun getCategoryById(categoryId: Long): TaskCategory? {
        return taskCategoryService.getCategories().first().firstOrNull {
            it.id == categoryId
        }
    }

    fun getTasksByStatus(categoryTasksUiState: CategoryTasksUiState, tabIndex: Int = 0) {
        viewModelScope.launch {
            val status =
                categoryTasksUiState.categoryTasksUiModel?.listOfTabBarItem?.get(tabIndex)?.title?.let { title ->
                    TaskStatusUiState.entries.firstOrNull { it.status == title }
                } ?: TaskStatusUiState.TODO

            taskService.getTasksByStatus(status.toDomain()).collect { tasks ->
                val uiTasks = tasks.map { it.toTaskUIModel() }
                _categoryTasksUiState.value = _categoryTasksUiState.value.copy(
                    categoryTasksUiModel = _categoryTasksUiState.value
                        .categoryTasksUiModel?.listOfTabBarItem?.mapIndexed { index, tabItem ->
                            if (index == tabIndex) {
                                tabItem.copy(
                                    isSelected = true,
                                    taskCount = tasks.size.toString()
                                )
                            } else {
                                tabItem.copy(isSelected = false)
                            }
                        }?.let {
                            _categoryTasksUiState.value.categoryTasksUiModel?.copy(
                                tasks = uiTasks,
                                selectedTabIndex = tabIndex,
                                listOfTabBarItem = it
                            )
                        }
                )
            }
        }
    }


}
