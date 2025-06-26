package com.example.tudee.presentation.themeViewModel

import androidx.lifecycle.ViewModel
import com.example.tudee.data.preferences.ThemePreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel(private val prefs: ThemePreferenceManager) : ViewModel() {
    private val _isDarkMode = MutableStateFlow(prefs.isDarkMode())
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleTheme(isDark: Boolean) {
        prefs.setDarkMode(isDark)
        _isDarkMode.value = isDark
    }
}
