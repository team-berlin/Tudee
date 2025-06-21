package com.example.tudee.data.mapper

import com.example.tudee.data.model.TaskEntity
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.toLocalDate


fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.name,
        categoryId = this.categoryId,
        status = this.status.name,
        assignedDate = this.assignedDate.toString()
    )
}

fun TaskEntity.toDomain(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = TaskPriority.valueOf(this.priority),
        categoryId = this.categoryId,
        status = TaskStatus.valueOf(this.status),
        assignedDate = this.assignedDate.toLocalDate()
    )
}

fun Flow<List<TaskEntity>>.toDomain(): Flow<List<Task>> {
    return map { it.map(TaskEntity::toDomain) }
}