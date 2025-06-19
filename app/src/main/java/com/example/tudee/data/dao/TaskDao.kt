package com.example.tudee.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudee.data.model.TaskEntity
import com.example.tudee.domain.entity.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTask(task: TaskEntity)


    @Query("DELETE FROM task_table WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Update
    suspend fun editTask(task: TaskEntity)

    @Query("UPDATE task_table SET status = :status WHERE id = :taskId")
    suspend fun editTaskStatus(taskId: Long, status: TaskStatus)

    @Query("SELECT * FROM task_table")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity

    @Query("SELECT * FROM task_table WHERE category_id = :categoryId")
    fun getTasksByCategoryId(categoryId: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE status = :status")
    fun getTasksByStatus(status: TaskStatus): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE assigned_date = :date")
    fun getTasksByAssignedDate(date: String): Flow<List<TaskEntity>>

    @Query("SELECT COUNT(*) FROM task_table WHERE category_id = :categoryId")
    suspend fun getTasksCountByCategoryId(categoryId: Long): Long
}