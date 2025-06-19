package com.example.tudee.presentation.categories.model

data class TaskCategoryUiModel(
    val id: Long,
    val name: String,
    val iconResId: UiImage,
    val tasksCount: Int,
    val isPredefined: Boolean = false
)
