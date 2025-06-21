package com.example.tudee.data.service

import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.data.mapper.toDomain
import com.example.tudee.data.mapper.toEntity
import com.example.tudee.data.model.TaskCategoryEntity
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.request.CategoryCreationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskCategoryServiceImpl(
    private val taskCategoryDao: TaskCategoryDao
) : TaskCategoryService {
    override suspend fun createCategory(taskCreationRequest: CategoryCreationRequest) {
        taskCategoryDao.createCategory(
            TaskCategoryEntity(
                id = 0,
                title = taskCreationRequest.title,
                isPredefined = taskCreationRequest.isPredefined,
                image = taskCreationRequest.image
            )
        )
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

    override suspend fun getCategoryById(categoryId: Long): Flow<TaskCategory> {
        return taskCategoryDao.getCategoryById(categoryId).map {
            it.toDomain()
        }
    }

    override suspend fun getCategoryIconById(categoryIconId: Long): String {
        return taskCategoryDao.getCategoryIconById(categoryIconId)
    }
}