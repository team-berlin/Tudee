package com.example.tudee.presentation.screen.category.model

data class CategoriesUiState(
    val categories: List<TaskCategoryUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
