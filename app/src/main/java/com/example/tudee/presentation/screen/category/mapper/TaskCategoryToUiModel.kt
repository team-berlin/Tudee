package com.example.tudee.presentation.screen.category.mapper

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.presentation.screen.category.model.TaskCategoryUiModel
import com.example.tudee.presentation.screen.category.model.UiImage
import com.example.tudee.presentation.utils.toCategoryIcon

fun TaskCategory.toUiModel(taskCount: Int): TaskCategoryUiModel {
    val uiImage: UiImage = if (isPredefined) {
        UiImage.Drawable(this.image.toCategoryIcon())
    } else {
        UiImage.External(image)
    }

    return TaskCategoryUiModel(
        id = id,
        name = title,
        categoryImage = uiImage,
        tasksCount = taskCount,
        isPredefined = isPredefined
    )
}