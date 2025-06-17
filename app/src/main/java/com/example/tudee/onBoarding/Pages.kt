package com.example.tudee.onBoarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tudee.R

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
)

val onboardingPages = listOf(
    Page(
        title = R.string.on_boarding_title1,
        description = R.string.on_boarding_description1,
        image = R.drawable.tudee_onboarding_1
    ),
    Page(
        title = R.string.on_boarding_title2,
        description = R.string.on_boarding_description2,
        image = R.drawable.tudee_onboarding_2,
    ),
    Page(
        title = R.string.on_boarding_title3,
        description = R.string.on_boarding_description3,
        image = R.drawable.tudee_onboarding_3,
    )
)