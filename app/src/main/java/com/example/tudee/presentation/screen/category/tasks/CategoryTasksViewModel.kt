package com.example.tudee.presentation.screen.category.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.presentation.screen.category.CategoriesConstants.CATEGORY_ID_ARGUMENT_KEY
import com.example.tudee.presentation.screen.category.model.CategoryData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryTasksViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskService,
    private val taskCategoryService: TaskCategoryService
) : ViewModel() {

    private val categoryId: Long = checkNotNull(savedStateHandle[CATEGORY_ID_ARGUMENT_KEY])
    private val _categoryTasksUiState = MutableStateFlow(CategoryTasksUiState(loading = true))
    val categoryTasksUiState: StateFlow<CategoryTasksUiState> = _categoryTasksUiState

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    private val _allTasks = MutableStateFlow<MutableList<TaskUIModel>>(mutableListOf())
    val allTasks = _allTasks.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getTasksByCategoryId(categoryId)
                val tasks =
                    taskService.getTasksByCategoryId(categoryId).first().map { it.toTaskUIModel() }
                _allTasks.value.addAll(tasks)
            } catch (_: Exception) {
                _snackBarEvent.emit(SnackBarEvent.ShowError)
            }
        }
        viewModelScope.launch {
            _categoryTasksUiState.collect { state ->
                if (!state.loading && state.categoryTasksUiModel != null) {
                    getTasksByStatus(state, state.categoryTasksUiModel.selectedTabIndex)
                }
            }
        }
    }

    suspend fun getTasksByCategoryId(categoryId: Long) {
        _categoryTasksUiState.value = _categoryTasksUiState.value.copy(loading = true)
        try {
            taskService.getTasksByCategoryId(categoryId)
                .map { tasks -> groupTasksByCategory(tasks) }
                .collect { categoryTasksUiModel ->
                    _categoryTasksUiState.value = if (categoryTasksUiModel.isNotEmpty()) {
                        _categoryTasksUiState.value.copy(
                            loading = false,
                            categoryTasksUiModel = categoryTasksUiModel.first()
                        )
                    } else {
                        _categoryTasksUiState.value.copy(
                            loading = false,
                            categoryTasksUiModel = getCategoryById(categoryId).toTaskCategoryUiModel(
                                emptyList()
                            )
                        )
                    }
                }
            _categoryTasksUiState.value.categoryTasksUiModel?.selectedTabIndex?.let { tabIndex ->
                getTasksByStatus(_categoryTasksUiState.value, tabIndex)
            }
        } catch (_: Exception) {
            _categoryTasksUiState.value = _categoryTasksUiState.value.copy(loading = false)
            _snackBarEvent.emit(SnackBarEvent.ShowError)
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
                        image = category.uiImage?.asString()!!,
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
                _categoryTasksUiState.update { currentState ->
                    currentState.copy(categoryTasksUiModel = category.toTaskCategoryUiModel(tasks))
                }
                category.toTaskCategoryUiModel(tasksInCategory)
            }
    }

    private suspend fun getCategoryById(categoryId: Long): TaskCategory {
        return taskCategoryService.getCategoryById(categoryId).first()
    }

    fun getTasksByStatus(categoryTasksUiState: CategoryTasksUiState, tabIndex: Int = 0) {
        viewModelScope.launch {
            val status =
                categoryTasksUiState
                    .categoryTasksUiModel?.listOfTabBarItem?.get(tabIndex)?.title?.let { title ->
                        TaskStatusUiState.entries.firstOrNull { it.status == title }
                    } ?: TaskStatusUiState.TODO

            taskService.getTasksByStatus(status.toDomain()).collect { allTasks ->
                val categoryTasks = allTasks.filter { it.categoryId == categoryId }
                val uiTasks = categoryTasks.map { it.toTaskUIModel() }
                _categoryTasksUiState.update { currentState ->
                    currentState.copy(
                        categoryTasksUiModel = currentState.categoryTasksUiModel?.copy(
                            tasks = uiTasks,
                            selectedTabIndex = tabIndex,
                            listOfTabBarItem = currentState.categoryTasksUiModel
                                .listOfTabBarItem.mapIndexed { index, tabItem ->
                                    if (index == tabIndex) {
                                        tabItem.copy(
                                            isSelected = true,
                                            taskCount = categoryTasks.size.toString()
                                        )
                                    } else {
                                        tabItem.copy(isSelected = false)
                                    }
                                }
                        )
                    )
                }
            }
        }
    }

    fun updateSelectedIndex(index: Int) {
        _categoryTasksUiState.value = _categoryTasksUiState.value.copy(
            categoryTasksUiModel = _categoryTasksUiState.value.categoryTasksUiModel?.copy(
                selectedTabIndex = index,
            )
        )
        getTasksByStatus(_categoryTasksUiState.value, index)
    }
}
