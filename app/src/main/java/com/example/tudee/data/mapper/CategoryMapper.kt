package com.example.tudee.data.mapper

import com.example.tudee.data.model.TaskCategoryEntity
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.ui.home.viewmodel.CategoryUiState

fun TaskCategoryEntity.toDomain(): TaskCategory {
    return TaskCategory(
        id = this.id,
        title = this.title,
        image = this.image,
        isPredefined = this.isPredefined
    )
}

fun TaskCategory.toEntity(): TaskCategoryEntity {
    return TaskCategoryEntity(
        id = this.id,
        title = this.title,
        image = this.image,
        isPredefined = this.isPredefined
    )
}

fun TaskCategory.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        id = this.id.toString(),
        title = this.title,
        image = this.image.toInt(),
        isPredefined = this.isPredefined
    )
}