package com.example.tudee.data.service

import com.example.tudee.data.dao.TaskDao
import com.example.tudee.data.mapper.toDomain
import com.example.tudee.data.mapper.toEntity
import com.example.tudee.data.model.TaskEntity
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class TaskServiceImpl(
    private val taskDao: TaskDao
) : TaskService {
    override suspend fun createTask(taskCreationRequest: TaskCreationRequest) {
        return taskDao.createTask(
            TaskEntity(
                id = 0,
                title = taskCreationRequest.title,
                description = taskCreationRequest.description,
                priority = taskCreationRequest.priority.name,
                categoryId = taskCreationRequest.categoryId,
                status = taskCreationRequest.status.name,
                assignedDate = taskCreationRequest.assignedDate.toString()
            )
        )
    }

    override suspend fun deleteTask(taskId: Long) {
        taskDao.deleteTask(taskId)
    }

    override suspend fun editTask(task: Task) {
        taskDao.editTask(task.toEntity())
    }

    override suspend fun editTaskStatus(
        taskId: Long,
        status: TaskStatus
    ) {
        taskDao.editTaskStatus(taskId, status)
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks().map { it.map(TaskEntity::toDomain) }
    }

    override suspend fun getTaskById(taskId: Long): Task {
        return taskDao.getTaskById(taskId).toDomain()
    }

    override fun getTasksByCategoryId(categoryId: Long): Flow<List<Task>> {
        return taskDao.getTasksByCategoryId(categoryId).toDomain()
    }

    override fun getTasksByStatus(status: TaskStatus): Flow<List<Task>> {
        return taskDao.getTasksByStatus(status).toDomain()
    }

    override fun getTasksByAssignedDate(date: LocalDate): Flow<List<Task>> {
        return taskDao.getTasksByAssignedDate(date.toString()).toDomain()
    }

    override suspend fun getTasksCountByCategoryId(categoryId: Long): Long {
        return taskDao.getTasksCountByCategoryId(categoryId)
    }
}
