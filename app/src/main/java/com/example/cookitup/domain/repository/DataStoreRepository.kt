package com.example.cookitup.domain.repository

import com.example.cookitup.ui.screens.settings.Theme

interface DataStoreRepository {
    suspend fun setTheme(theme: Theme)
}
