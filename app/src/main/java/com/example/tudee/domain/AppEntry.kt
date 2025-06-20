package com.example.tudee.domain

interface AppEntry {
    suspend fun saveFirstEntry()
    suspend fun isFirstEntry(): Boolean
}