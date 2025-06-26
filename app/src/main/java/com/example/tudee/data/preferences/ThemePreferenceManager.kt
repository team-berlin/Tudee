package com.example.tudee.data.preferences


import android.content.Context

class ThemePreferenceManager(context: Context) {
    private val prefs = context.getSharedPreferences("tudee_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
    }

    fun isDarkMode(): Boolean {
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkMode(isDark: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, isDark).apply()
    }
}