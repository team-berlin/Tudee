package com.example.tudee.presentation.categories.model


data class TaskCategoryUiModel(
    val id: Long,
    val name: String,
    val iconResId: String,
    val count: Int = 0,
    val isPredefined: Boolean = false
)
