package com.example.tudee.presentation.categories.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tudee.domain.repository.CategoryRepository
import com.example.tudee.presentation.categories.model.CategoriesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoriesViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    fun loadCategories() {
        // fetch categories from use case
    }

    fun addCategory(name: String, iconUrl: String) {
        // validate and add to DB
    }

    fun onCategoryClicked(id: Long) {
        // maybe emit navigation event if needed
    }
}