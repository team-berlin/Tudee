package com.example.tudee.domain

import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TaskService {
    suspend fun createTask(taskCreationRequest: TaskCreationRequest)
    suspend fun deleteTask(taskId: Long)
    suspend fun editTask(task: Task)
    suspend fun editTaskStatus(taskId: Long, status: TaskStatus)
    fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(taskId: Long): Task
    fun getTasksByCategoryId(categoryId: Long): Flow<List<Task>>
    fun getTasksByStatus(status: TaskStatus):Flow<List<Task>>
    fun getTasksByAssignedDate(date: LocalDate): Flow<List<Task>>
    suspend fun getTasksCountByCategoryId(categoryId: Long): Long
}