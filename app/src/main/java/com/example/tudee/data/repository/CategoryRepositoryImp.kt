package com.example.tudee.data.repository

import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.repository.CategoryRepository

class CategoryRepositoryImp: CategoryRepository {
    override suspend fun getCategories(): List<TaskCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun addCategory(category: TaskCategory) {
        TODO("Not yet implemented")
    }
}