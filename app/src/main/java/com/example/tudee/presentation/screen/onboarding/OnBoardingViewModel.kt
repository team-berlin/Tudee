package com.example.tudee.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.AppEntry
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.presentation.utils.predefinedCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val taskCategoryService: TaskCategoryService, private val appEntry: AppEntry
) : ViewModel() {

    private val _isFirstEntry = MutableStateFlow(true)
    val isFirstEntry = _isFirstEntry.asStateFlow()

    init {
        viewModelScope.launch {
            _isFirstEntry.value = appEntry.isFirstEntry()
        }
    }

    fun saveFirstEntry() {
        viewModelScope.launch {
            appEntry.saveFirstEntry()
        }
    }

    fun loadInitialData() {
        viewModelScope.launch {
            predefinedCategories.forEach { taskCategoryService.createCategory(it) }
        }
    }
}
