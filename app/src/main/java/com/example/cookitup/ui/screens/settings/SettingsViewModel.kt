package com.example.cookitup.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.data.repository.DataStoreRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class State(val theme: Theme)

interface SettingsActions {
    fun changeTheme(theme: Theme)
}

class SettingsViewModel(
    private val repository: DataStoreRepositoryImpl
) : ViewModel() {
    val state = repository.theme.map { State(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = State(Theme.System)
    )

    val actions = object : SettingsActions {
        override fun changeTheme(theme: Theme) {
            viewModelScope.launch {
                repository.setTheme(theme)
            }
        }
    }
}
