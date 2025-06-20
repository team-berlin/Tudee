package com.example.tudee.presentation.screen.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.presentation.screen.category.mapper.toUiModel
import com.example.tudee.presentation.screen.category.model.CategoriesUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val taskCategoryService: TaskCategoryService,
    private val taskService: TaskService
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = taskCategoryService.getCategories().first()

                val categoriesWithCount = categories.map { category ->
                    async {
                        val count = taskService.getTasksCountByCategoryId(category.id)
                        category to count.toInt()
                    }
                }.awaitAll()


                val uiModels = categoriesWithCount.map { (category, count) ->
                    category.toUiModel(count)
                }

                _uiState.update { it.copy(categories = uiModels) }

            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Unknown error") }
            }
        }
    }

    fun addCategory(name: String, iconUrl: String) {
        // TODO: Add validation and call taskCategoryService.createCategory()
    }

    fun onCategoryClicked(id: Long) {
        // TODO: Emit navigation event or update state if needed
    }
}


