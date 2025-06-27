package com.example.tudee.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.data.preferences.PreferencesManager
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.presentation.utils.predefinedCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val taskCategoryService: TaskCategoryService,
    private val prefs: PreferencesManager
) : ViewModel() {

    private val _isFirstEntry = MutableStateFlow(prefs.isOnboardingCompleted())
    val isFirstEntry = _isFirstEntry.asStateFlow()

    fun saveFirstEntry() = prefs.setOnboardingCompleted()

    fun loadInitialData() {
        viewModelScope.launch {
            predefinedCategories.forEach { taskCategoryService.createCategory(it) }
        }
    }
}
