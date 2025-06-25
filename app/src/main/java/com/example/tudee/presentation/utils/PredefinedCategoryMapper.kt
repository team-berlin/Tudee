package com.example.tudee.presentation.utils

import com.example.tudee.R
import com.example.tudee.domain.request.CategoryCreationRequest

val predefinedCategories = listOf(
    CategoryCreationRequest(
        title = "Education",
        isPredefined = true,
        image = "education"
    ),
    CategoryCreationRequest(
        title = "Shopping",
        isPredefined = true,
        image = "shopping"
    ),
    CategoryCreationRequest(
        title = "Medical",
        isPredefined = true,
        image = "medical"
    ),
    CategoryCreationRequest(
        title = "Gym",
        isPredefined = true,
        image = "gym"
    ),
    CategoryCreationRequest(
        title = "Entertainment",
        isPredefined = true,
        image = "entertainment"
    ),
    CategoryCreationRequest(
        title = "Event",
        isPredefined = true,
        image = "event"
    ),
    CategoryCreationRequest(
        title = "work",
        isPredefined = true,
        image = "work"
    ),
    CategoryCreationRequest(
        title = "Budgeting",
        isPredefined = true,
        image = "budgeting"
    ),
    CategoryCreationRequest(
        title = "Self-care",
        isPredefined = true,
        image = "Self_care"
    ),
    CategoryCreationRequest(
        title = "Adoration",
        isPredefined = true,
        image = "adoration"
    ),
    CategoryCreationRequest(
        title = "Fixing bugs",
        isPredefined = true,
        image = "fixing_bugs"
    ),
    CategoryCreationRequest(
        title = "Cleaning",
        isPredefined = true,
        image = "cleaning"
    ),
    CategoryCreationRequest(
        title = "Traveling",
        isPredefined = true,
        image = "traveling"
    ),
    CategoryCreationRequest(
        title = "Agriculture",
        isPredefined = true,
        image = "agriculture"
    ),
    CategoryCreationRequest(
        title = "Coding",
        isPredefined = true,
        image = "coding"
    ),
    CategoryCreationRequest(
        title = "Cooking",
        isPredefined = true,
        image = "cooking"
    ),
    CategoryCreationRequest(
        title = "Family & friend",
        isPredefined = true,
        image = "family_friend"
    ),
)

val categoryIcon = mapOf(
    "education" to R.drawable.education,
    "shopping" to R.drawable.shopping,
    "medical" to R.drawable.medical,
    "gym" to R.drawable.gym,
    "entertainment" to R.drawable.entertainment,
    "event" to R.drawable.event,
    "work" to R.drawable.work,
    "budgeting" to R.drawable.budgeting,
    "Self_care" to R.drawable.self_care,
    "adoration" to R.drawable.adoration,
    "fixing_bugs" to R.drawable.fixing_bugs,
    "cleaning" to R.drawable.cleaning,
    "traveling" to R.drawable.travelling,
    "agriculture" to R.drawable.agriculture,
    "coding" to R.drawable.coding,
    "cooking" to R.drawable.coocking,
    "family_friend" to R.drawable.family_friend,
)

fun String.toCategoryIcon(): Int {
    return categoryIcon[this] ?: -1
}
