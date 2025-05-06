package com.example.kotlinquiz.data.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * PUBLIC_INTERFACE
 * Manages user preferences for the quiz application using SharedPreferences
 */
@Singleton
class UserPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * PUBLIC_INTERFACE
     * Theme preference (light/dark)
     */
    var isDarkTheme: Boolean
        get() = prefs.getBoolean(KEY_DARK_THEME, false)
        set(value) = prefs.edit().putBoolean(KEY_DARK_THEME, value).apply()

    /**
     * PUBLIC_INTERFACE
     * Notification preference
     */
    var notificationsEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATIONS, true)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATIONS, value).apply()

    /**
     * PUBLIC_INTERFACE
     * Default quiz difficulty (0: Easy, 1: Medium, 2: Hard)
     */
    var defaultDifficulty: Int
        get() = prefs.getInt(KEY_DEFAULT_DIFFICULTY, 0)
        set(value) = prefs.edit().putInt(KEY_DEFAULT_DIFFICULTY, value).apply()

    /**
     * PUBLIC_INTERFACE
     * Default time limit for quizzes in minutes
     */
    var defaultTimeLimit: Int
        get() = prefs.getInt(KEY_DEFAULT_TIME_LIMIT, 30)
        set(value) = prefs.edit().putInt(KEY_DEFAULT_TIME_LIMIT, value).apply()

    companion object {
        private const val PREFS_NAME = "quiz_preferences"
        private const val KEY_DARK_THEME = "dark_theme"
        private const val KEY_NOTIFICATIONS = "notifications"
        private const val KEY_DEFAULT_DIFFICULTY = "default_difficulty"
        private const val KEY_DEFAULT_TIME_LIMIT = "default_time_limit"
    }
}
