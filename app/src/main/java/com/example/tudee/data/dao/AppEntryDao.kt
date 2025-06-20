package com.example.tudee.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tudee.data.model.AppEntryEntity

@Dao
interface AppEntryDao {
    @Query("SELECT * FROM app_entry LIMIT 1")
    suspend fun getAppEntry(): AppEntryEntity?

    @Insert
    suspend fun insertAppEntry(appEntry: AppEntryEntity)
}