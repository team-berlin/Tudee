package com.example.tudee.domain

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.request.CategoryCreationRequest
import kotlinx.coroutines.flow.Flow

interface TaskCategoryService {
    suspend fun createCategory(taskCreationRequest: CategoryCreationRequest)
    suspend fun editCategory(category: TaskCategory)
    suspend fun deleteCategory(categoryId: Long)
    fun getCategories(): Flow<List<TaskCategory>>
    suspend fun getCategoryById(categoryId: Long): Flow<TaskCategory>
    suspend fun getCategoryIconById(categoryIconId: Long): String
}