package com.example.tudee.domain

import com.example.tudee.domain.entity.TaskCategory
import kotlinx.coroutines.flow.Flow

interface TaskCategoryService {
    suspend fun createCategory(category: TaskCategory)
    suspend fun editCategory(category: TaskCategory)
    suspend fun deleteCategory(categoryId: Long)
    fun getCategories(): Flow<List<TaskCategory>>
    fun getCategoryById(categoryId: Long): Flow<TaskCategory>
}