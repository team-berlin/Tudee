package com.example.tudee.data.service

import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.data.mapper.toDomain
import com.example.tudee.data.mapper.toEntity
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.service.TaskCategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskCategoryServiceImpl(
    private val taskCategoryDao: TaskCategoryDao
) : TaskCategoryService {
    override suspend fun createCategory(category: TaskCategory) {
        taskCategoryDao.createCategory(category.toEntity())
    }

    override suspend fun editCategory(category: TaskCategory) {
        taskCategoryDao.editCategory(category.toEntity())
    }

    override suspend fun deleteCategory(categoryId: Long) {
        taskCategoryDao.deleteCategory(categoryId)
    }

    override fun getCategories(): Flow<List<TaskCategory>> {
       return taskCategoryDao.getCategories().map {
           it.map { it.toDomain() }
       }
    }
}