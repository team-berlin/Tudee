package com.example.tudee.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_category_table")
data class TaskCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "is_predefined")
    val isPredefined: Boolean
)