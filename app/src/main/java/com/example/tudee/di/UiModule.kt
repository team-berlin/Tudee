package com.example.tudee.di


import com.example.tudee.presentation.screen.onboarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    // single<AppEntry> {}
  viewModel { OnBoardingViewModel(get()) }
    //single { OnBoardingScreen(get()) }
}