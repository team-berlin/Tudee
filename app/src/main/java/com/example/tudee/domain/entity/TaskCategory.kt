package com.example.tudee.domain.entity

data class TaskCategory(
    val id: Long,
    val title:String,
    val image: String,
    val isPredefined: Boolean
)