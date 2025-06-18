package com.example.tudee.domain.repository

import com.example.tudee.domain.entity.TaskCategory

interface CategoryRepository {
    suspend fun getCategories(): List<TaskCategory>
    suspend fun addCategory(category: TaskCategory)
}