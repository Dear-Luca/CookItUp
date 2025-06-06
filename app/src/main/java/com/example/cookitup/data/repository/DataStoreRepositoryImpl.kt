package com.example.cookitup.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cookitup.domain.repository.DataStoreRepository
import com.example.cookitup.ui.screens.settings.Theme
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
    }
    val theme = dataStore.data
        .map { preferences ->
            try {
                Theme.valueOf(preferences[THEME_KEY] ?: "System")
            } catch (e: Exception) {
                Theme.System
            }
        }
    override suspend fun setTheme(theme: Theme) {
        dataStore.edit { it[THEME_KEY] = theme.toString() }
    }
}
