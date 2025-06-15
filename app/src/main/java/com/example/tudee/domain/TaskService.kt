package com.example.tudee.domain

import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import kotlinx.datetime.LocalDate

interface TaskService {
    suspend fun createTask(taskCreationRequest: TaskCreationRequest)
    suspend fun deleteTask(taskId: Long)
    suspend fun editTask(task: Task)
    suspend fun editTaskStatus(taskId: Long, status: TaskStatus)
    suspend fun getTasks(): List<Task>
    suspend fun getTaskById(taskId: Long): Task
    suspend fun getTasksByCategoryId(categoryId: Long): List<Task>
    suspend fun getTasksByStatus(status: TaskStatus): List<Task>
    suspend fun getTasksByAssignedDate(date: LocalDate): List<Task>
    suspend fun getTasksCountByCategoryId(categoryId: Long): Long
}