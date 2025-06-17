package com.example.tudee.presentation.categories.mapper

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.presentation.categories.model.TaskCategoryUiModel

fun TaskCategory.toUiModel(count: Int = 0): TaskCategoryUiModel {
    return TaskCategoryUiModel(
        id = id,
        name = title,
        iconResId = image,
        count = count,
        isPredefined = isPredefined
    )
}