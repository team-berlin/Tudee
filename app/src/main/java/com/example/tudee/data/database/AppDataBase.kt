package com.example.tudee.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tudee.data.dao.AppEntryDao
import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.data.dao.TaskDao
import com.example.tudee.data.model.AppEntryEntity
import com.example.tudee.data.model.TaskCategoryEntity
import com.example.tudee.data.model.TaskEntity

@Database(
    entities = [TaskEntity::class, TaskCategoryEntity::class, AppEntryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskCategoryDao(): TaskCategoryDao
    abstract fun appEntryDao(): AppEntryDao
}