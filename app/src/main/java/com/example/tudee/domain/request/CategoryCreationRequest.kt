package com.example.tudee.domain.request

data class CategoryCreationRequest(
    val title: String,
    val isPredefined: Boolean = false,
    val image: String,
)
