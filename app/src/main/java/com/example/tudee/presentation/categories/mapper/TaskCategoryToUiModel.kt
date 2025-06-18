package com.example.tudee.presentation.categories.mapper

import com.example.tudee.R
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.presentation.categories.model.TaskCategoryUiModel
import com.example.tudee.presentation.categories.model.UiImage

fun TaskCategory.toUiModel(tasks: List<Task>): TaskCategoryUiModel {
    val uiImage: UiImage = if (isPredefined) {
        // Safe fallback if key not found
        val resId = R.drawable.tudee //handle predefined categories
        UiImage.Drawable(resId)
    } else {
        UiImage.Url(image)
    }

    return TaskCategoryUiModel(
        id = id,
        name = title,
        iconResId = uiImage,
        tasks = tasks,
        isPredefined = isPredefined
    )
}