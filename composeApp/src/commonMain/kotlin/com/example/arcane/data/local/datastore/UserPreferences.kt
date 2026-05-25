package com.example.arcane.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * User Preferences menggunakan DataStore untuk aplikasi Arcane
 */
class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {
    // ==================== PREFERENCE KEYS ====================
    
    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val READING_GOAL = intPreferencesKey("reading_goal")
    }
    
    // ==================== DARK MODE ====================
    
    /**
     * Observe dark mode setting
     */
    val isDarkMode: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[Keys.DARK_MODE] ?: false
    }
    
    /**
     * Set dark mode
     */
    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.DARK_MODE] = enabled
        }
    }
    
    // ==================== READING GOAL ====================
    
    /**
     * Observe reading goal (target number of books to read)
     */
    val readingGoal: Flow<Int> = dataStore.data.map { prefs ->
        prefs[Keys.READING_GOAL] ?: 12 // Default target is 12 books
    }
    
    /**
     * Set reading goal
     */
    suspend fun setReadingGoal(goal: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.READING_GOAL] = goal
        }
    }
}
