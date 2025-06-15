package com.example.tudee.presentation.categories.model

import androidx.compose.ui.graphics.Color

data class TaskCategoryUiModel(
    val id: Int,
    val name: String,
    val iconResId: UiImage,
    val count: Int = 0,
    val isPredefined: Boolean = false
)
