package com.example.tudee.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_entry")
data class AppEntryEntity(
    @PrimaryKey val id: Int = 1,
    val hasSeenOnboarding: Boolean = true
)