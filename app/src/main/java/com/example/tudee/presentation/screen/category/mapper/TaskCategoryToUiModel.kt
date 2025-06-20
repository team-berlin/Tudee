package com.example.tudee.presentation.screen.category.mapper

import com.example.tudee.R
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.presentation.screen.category.model.TaskCategoryUiModel
import com.example.tudee.presentation.screen.category.model.UiImage

fun TaskCategory.toUiModel(taskCount: Int): TaskCategoryUiModel {
    val uiImage: UiImage = if (isPredefined) {
        // Safe fallback if key not found
        val resId = R.drawable.tudee //handle predefined categories
        UiImage.Drawable(resId)
    } else {
        UiImage.External(image)
    }

    return TaskCategoryUiModel(
        id = id,
        name = title,
        iconResId = uiImage,
        tasksCount = taskCount,
        isPredefined = isPredefined
    )
}