package com.example.tudee.presentation.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.presentation.categories.mapper.toUiModel
import com.example.tudee.presentation.categories.model.CategoriesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(private val taskCategoryService: TaskCategoryService) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            val categories = taskCategoryService.getCategories().first()

            val tasks
            _uiState.update {
                it.copy(
                    categories = categories.map { it.toUiModel(categories.size) },
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