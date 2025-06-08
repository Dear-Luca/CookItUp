package com.example.cookitup.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.data.repository.DataStoreRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ThemeState(val theme: Theme)

interface ThemeActions {
    fun changeTheme(theme: Theme)
}

class ThemeViewModel(
    private val dataStoreRepository: DataStoreRepositoryImpl
) : ViewModel() {

    val state = dataStoreRepository.theme.map { ThemeState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ThemeState(Theme.System)
    )

    val actions = object : ThemeActions {
        override fun changeTheme(theme: Theme) {
            viewModelScope.launch {
                dataStoreRepository.setTheme(theme)
            }
        }
    }
}
