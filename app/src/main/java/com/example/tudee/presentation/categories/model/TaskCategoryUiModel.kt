package com.example.tudee.presentation.categories.model

import com.example.tudee.domain.entity.Task


data class TaskCategoryUiModel(
    val id: Long,
    val name: String,
    val iconResId: UiImage,
    val tasks: List<Task>,
    val isPredefined: Boolean = false
)
