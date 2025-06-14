package com.example.tudee.domain

import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskRequest
import kotlinx.datetime.LocalDate

interface TaskService {
    suspend fun createTask(taskRequest: TaskRequest)
    suspend fun deleteTask(taskId: Int)
    suspend fun editTask(task: Task)
    suspend fun getTasks(): List<Task>
    suspend fun getTaskById(taskId: Int): Task
    suspend fun getTasksByCategoryId(categoryId: Int):List<Task>
    suspend fun getTasksByStatus(status: TaskStatus): List<Task>
    suspend fun getTasksByAssignedDate(date: LocalDate): List<Task>
    suspend fun getTasksCountByCategoryId(categoryId: Int): Int
    suspend fun createCategory(category: TaskCategory)
    suspend fun editCategory(category: TaskCategory)
    suspend fun deleteCategory(categoryId: Int)
    suspend fun getCategories(): List<TaskCategory>
    suspend fun editTaskStatus(taskId: Int, status: TaskStatus)
}