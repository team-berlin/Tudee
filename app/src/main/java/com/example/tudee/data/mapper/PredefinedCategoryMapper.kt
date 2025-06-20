package com.example.tudee.data.mapper

import com.example.tudee.R
import com.example.tudee.domain.request.CategoryCreationRequest

val predefinedCategories=listOf(
    CategoryCreationRequest(
        title = "Education",
        isPredefined =true ,
        image = "education"
    ),
    CategoryCreationRequest(
        title = "Shopping",
        isPredefined =true ,
        image = "shopping"
    ),
    CategoryCreationRequest(
        title = "Medical",
        isPredefined =true ,
        image = "medical"
    ), CategoryCreationRequest(
        title = "Gym",
        isPredefined =true ,
        image = "gym"
    ), CategoryCreationRequest(
        title = "Entertainment",
        isPredefined =true ,
        image = "entertainment"
    ), CategoryCreationRequest(
        title = "Event",
        isPredefined =true ,
        image = "event"
    ), CategoryCreationRequest(
        title = "Work",
        isPredefined =true ,
        image = "work"
    ), CategoryCreationRequest(
        title = "Budgeting",
        isPredefined =true ,
        image = "budgeting"
    ), CategoryCreationRequest(
        title = "Self-care",
        isPredefined =true ,
        image = "Self_care"
    ), CategoryCreationRequest(
        title = "Adoration",
        isPredefined =true ,
        image = "adoration"
    ), CategoryCreationRequest(
        title = "Fixing bugs",
        isPredefined =true ,
        image = "fixing_bugs"
    ),CategoryCreationRequest(
        title = "Cleaning",
        isPredefined =true ,
        image = "cleaning"
    ),CategoryCreationRequest(
        title = "Traveling",
        isPredefined =true ,
        image = "traveling"
    ),CategoryCreationRequest(
        title = "Agriculture",
        isPredefined =true ,
        image = "agriculture"
    ),
    CategoryCreationRequest(
        title = "Coding",
        isPredefined =true ,
        image = "coding"
    ),
    CategoryCreationRequest(
        title = "Cooking",
        isPredefined =true ,
        image = "cooking"
    ),CategoryCreationRequest(
        title = "Family & friend",
        isPredefined =true ,
        image = "family_friend"
    ),
)

fun getCategoryIcon(iconName:String):Int{
    when(iconName){
        "Education"-> return R.drawable.education
        "Shopping"-> return R.drawable.shopping
        "Medical"-> return  R.drawable.medical
        "Gym"-> return R.drawable.gym
        "Entertainment"-> return R.drawable.entertainment
        "Event"-> return R.drawable.event
        "Work"-> return  R.drawable.work
        "Budgeting"-> return  R.drawable.budgeting
        "Self-care"-> return  R.drawable.self_care
        "Adoration"-> return  R.drawable.adoration
        "Fixing bugs"-> return  R.drawable.fixing_bugs
        "Cleaning"-> return  R.drawable.cleaning
        "Traveling"-> return  R.drawable.travelling
        "Agriculture"-> return  R.drawable.agriculture
        "Coding"-> return  R.drawable.coding
        "Cooking"-> return  R.drawable.coocking
        "Family & friend"-> return  R.drawable.family_friend
        else -> return -1
    }
}