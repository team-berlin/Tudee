package com.example.tudee.presentation.screen.category.model

data class TaskCategoryUiModel(
    val id: Long,
    val name: String,
    val iconResId: UiImage,
    val tasksCount: Int,
    val isPredefined: Boolean = false
)
