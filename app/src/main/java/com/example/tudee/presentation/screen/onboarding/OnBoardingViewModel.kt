package com.example.tudee.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.AppEntry
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val appEntry: AppEntry
) : ViewModel() {

    fun saveFirstEntry() {
        viewModelScope.launch {
            appEntry.saveFirstEntry()
        }
    }
}