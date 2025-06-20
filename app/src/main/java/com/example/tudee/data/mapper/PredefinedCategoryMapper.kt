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
        title = "work",
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
        "education"-> return R.drawable.education
        "shopping"-> return R.drawable.shopping
        "medical"-> return  R.drawable.medical
        "gym"-> return R.drawable.gym
        "entertainment"-> return R.drawable.entertainment
        "event"-> return R.drawable.event
        "work"-> return  R.drawable.work
        "budgeting"-> return  R.drawable.budgeting
        "Self_care"-> return  R.drawable.self_care
        "adoration"-> return  R.drawable.adoration
        "fixing_bugs"-> return  R.drawable.fixing_bugs
        "cleaning"-> return  R.drawable.cleaning
        "traveling"-> return  R.drawable.travelling
        "agriculture"-> return  R.drawable.agriculture
        "coding"-> return  R.drawable.coding
        "cooking"-> return  R.drawable.coocking
        "family_friend"-> return  R.drawable.family_friend
        else -> return -1
    }
}