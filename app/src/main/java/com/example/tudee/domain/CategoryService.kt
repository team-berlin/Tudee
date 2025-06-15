package com.example.tudee.domain

import com.example.tudee.domain.entity.TaskCategory

interface CategoryService {
    suspend fun createCategory(category: TaskCategory)
    suspend fun editCategory(category: TaskCategory)
    suspend fun deleteCategory(categoryId: Long)
    suspend fun getCategories(): List<TaskCategory>
}