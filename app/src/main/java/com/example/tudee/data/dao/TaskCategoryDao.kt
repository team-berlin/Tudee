package com.example.tudee.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudee.data.model.TaskCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCategory(category: TaskCategoryEntity)

    @Update
    suspend fun editCategory(category: TaskCategoryEntity)

    @Query("DELETE FROM task_category_table WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Long)

    @Query("SELECT * FROM task_category_table")
    fun getCategories(): Flow<List<TaskCategoryEntity>>

    @Query("SELECT * FROM task_category_table WHERE id = :categoryId")
    fun getCategoryById(categoryId: Long): Flow<TaskCategoryEntity>

    @Query("SELECT image FROM task_category_table WHERE id = :categoryIconId")
    suspend fun getCategoryIconById(categoryIconId: Long): String
}