package com.example.tudee.presentation.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.categories.mapper.toUiModel
import com.example.tudee.presentation.categories.model.CategoriesUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class CategoriesViewModel(
    private val taskCategoryService: TaskCategoryService,
    private val taskService: TaskService
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    private fun getCategories(): Flow<List<TaskCategory>> {
        return flow {
            emit(
                listOf(
                    TaskCategory(
                        id = 1234,
                        title = "Category 1",
                        image = "TODO()",
                        isPredefined = true
                    )
                )
            )
        }
    }

    private fun getTasksById(id: Long): Flow<List<Task>> {
        return flow {
            emit(
                listOf(
                    Task(
                        id = 112233,
                        title = "Task1",
                        description = "Task 1 Description",
                        priority = TaskPriority.HIGH,
                        categoryId = 1234,
                        status = TaskStatus.IN_PROGRESS,
                        assignedDate = LocalDate(2025, 1, 1)
                    )
                )
            )
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getCategories().first()

                val categoryTasksDeferred = categories.map { category ->
                    async {
                        category to getTasksById(category.id).first()
                    }
                }

                val categoriesWithTasks =
                    categoryTasksDeferred.awaitAll().map { (category, categoryTasks) ->
                        category.toUiModel(categoryTasks)
                    }

                _uiState.update { state ->
                    state.copy(categories = categoriesWithTasks)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addCategory(name: String, iconUrl: String) {
        // validate and add to DB
    }

    fun onCategoryClicked(id: Long) {
        // maybe emit navigation event if needed
    }
}


