package com.example.tudee.presentation.screen.category.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.category.CategoriesConstants.CATEGORY_ID_ARGUMENT_KEY
import com.example.tudee.presentation.screen.category.model.CategoryData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryTasksViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskService,
    private val taskCategoryService: TaskCategoryService
) : ViewModel() {

    private val categoryId: Long = checkNotNull(savedStateHandle[CATEGORY_ID_ARGUMENT_KEY])

    private val _categoryTasksUiState = MutableStateFlow(CategoryTasksUiState())
    val categoryTasksUiState: StateFlow<CategoryTasksUiState> = _categoryTasksUiState

    private val _snackBarEvent = MutableSharedFlow<CategoryTasksSnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    private val _allTasks = MutableStateFlow<MutableList<TaskUIModel>>(mutableListOf())
    val allTasks = _allTasks.asStateFlow()

    fun showEditCategorySheet() {
        _categoryTasksUiState.update { currentState ->
            currentState.copy(isEditCategorySheetVisible = true)
        }
    }

    fun hideEditCategorySheet() {
        _categoryTasksUiState.update { currentState ->
            currentState.copy(isEditCategorySheetVisible = false)
        }
    }

    fun showDeleteCategorySheet() {
        _categoryTasksUiState.update { currentState ->
            currentState.copy(isDeleteCategorySheetVisible = true)
        }
    }

    fun hideDeleteCategorySheet() {
        _categoryTasksUiState.update { currentState ->
            currentState.copy(isDeleteCategorySheetVisible = false)
        }
    }

    init {
        viewModelScope.launch {
            _categoryTasksUiState.update { it.copy(loading = true) }

            try {
                val tasks = getTasksByCategoryId(categoryId)
                _allTasks.update {
                    val updatedList = it.toMutableList()
                    updatedList.addAll(tasks.map { task -> task.toTaskUIModel() })
                    updatedList
                }
                val categoryUiModel = getCategoryById(categoryId).toTaskCategoryUiModel(tasks)
                _categoryTasksUiState.update { currentState ->
                    currentState.copy(
                        loading = false,
                        categoryTasksUiModel = categoryUiModel
                    )
                }
                updateSelectedIndex(0)
            } catch (e: Exception) {
                _categoryTasksUiState.update { currentState ->
                    currentState.copy(
                        loading = false,
                    )
                }
            }
        }
    }

    private suspend fun getCategoryById(categoryId: Long): TaskCategory {
        return taskCategoryService.getCategoryById(categoryId).first()
    }

    private suspend fun getTasksByCategoryId(categoryId: Long): List<Task> {
        return taskService.getTasksByCategoryId(categoryId).first()
    }

    private suspend fun getTasksByState(state: TaskStatus): List<TaskUIModel> {
        return taskService.getTasksByStatus(state).first().map { it.toTaskUIModel() }
    }

    fun updateSelectedIndex(tabIndex: Int) {
        viewModelScope.launch {
            _categoryTasksUiState.value.categoryTasksUiModel?.let { categoryTasksUiModel ->
                val filteredTasks =
                    getTasksByState(tabIndex.toTaskStatus()).filter { it.categoryId == categoryId }
                _categoryTasksUiState.update { currentState ->
                    currentState.copy(
                        categoryTasksUiModel = currentState.categoryTasksUiModel?.copy(
                            tasks = filteredTasks,
                            selectedTabIndex = tabIndex,
                            listOfTabBarItem = categoryTasksUiModel.listOfTabBarItem.mapIndexed { index, tabItem ->
                                if (index == tabIndex) {
                                    tabItem.copy(
                                        isSelected = true,
                                        taskCount = filteredTasks.size.toString()
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

    private fun Int.toTaskStatus(): TaskStatus {
        return when (this) {
            0 -> TaskStatus.IN_PROGRESS
            1 -> TaskStatus.TODO
            2 -> TaskStatus.DONE
            else -> {
                TaskStatus.TODO
            }
        }
    }

    fun deleteCategory() {
        _categoryTasksUiState.value.categoryTasksUiModel?.let {
            viewModelScope.launch {
                try {
                    taskCategoryService.deleteCategory(it.id)
                    _snackBarEvent.emit(CategoryTasksSnackBarEvent.ShowDeleteSuccess)
                    _categoryTasksUiState.value.categoryTasksUiModel?.let {
                        if (it.tasks.isNotEmpty()) {
                            it.tasks.forEach {
                                taskService.deleteTask(it.id)
                            }
                        }
                    }
                    hideDeleteCategorySheet()
                    hideEditCategorySheet()
                } catch (_: Exception) {
                    _snackBarEvent.emit(CategoryTasksSnackBarEvent.ShowDeleteError)
                }
            }
        }
    }

    fun editCategory(category: CategoryData) {
        _categoryTasksUiState.value.categoryTasksUiModel?.let { categoryTasksUiModel ->
            viewModelScope.launch {
                try {
                    val taskCategory = TaskCategory(
                        id = categoryTasksUiModel.id,
                        title = category.name,
                        image = category.uiImage?.asString()!!,
                        isPredefined = categoryTasksUiModel.isPredefined
                    )
                    taskCategoryService.editCategory(taskCategory)
                    updateState(taskCategory, categoryTasksUiModel.tasks)
                    hideEditCategorySheet()
                    _snackBarEvent.emit(CategoryTasksSnackBarEvent.ShowEditSuccess)
                } catch (_: Exception) {
                    _snackBarEvent.emit(CategoryTasksSnackBarEvent.ShowEditError)
                }
            }
        }
    }

    private fun updateState(taskCategory: TaskCategory, tasks: List<TaskUIModel>) {
        _categoryTasksUiState.value.categoryTasksUiModel?.let { categoryTasksUiModel ->
            _categoryTasksUiState.update {
                it.copy(
                    categoryTasksUiModel = taskCategory
                        .toTaskCategoryUiModel(
                            tasks = tasks.map { it.toDomain() }
                        )
                )
            }
        }

    }
}


sealed class CategoryTasksSnackBarEvent() {
    data object ShowEditError : CategoryTasksSnackBarEvent()
    data object ShowEditSuccess : CategoryTasksSnackBarEvent()
    data object ShowDeleteError : CategoryTasksSnackBarEvent()
    data object ShowDeleteSuccess : CategoryTasksSnackBarEvent()
}