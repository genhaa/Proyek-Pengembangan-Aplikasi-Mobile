package com.example.arcane.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val READING_GOAL = intPreferencesKey("reading_goal")
        val USER_NAME = stringPreferencesKey("user_name")
        val FAVORITE_GENRE = stringPreferencesKey("favorite_genre")
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[Keys.DARK_MODE] ?: false
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.DARK_MODE] = enabled
        }
    }

    val readingGoal: Flow<Int> = dataStore.data.map { prefs ->
        prefs[Keys.READING_GOAL] ?: 12
    }

    suspend fun setReadingGoal(goal: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.READING_GOAL] = goal
        }
    }

    val userName: Flow<String> = dataStore.data.map { prefs ->
        prefs[Keys.USER_NAME] ?: ""
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { prefs ->
            prefs[Keys.USER_NAME] = name
        }
    }

    val favoriteGenre: Flow<String> = dataStore.data.map { prefs ->
        prefs[Keys.FAVORITE_GENRE] ?: ""
    }

    suspend fun setFavoriteGenre(genre: String) {
        dataStore.edit { prefs ->
            prefs[Keys.FAVORITE_GENRE] = genre
        }
    }
}