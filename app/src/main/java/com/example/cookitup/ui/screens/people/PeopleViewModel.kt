package com.example.cookitup.ui.screens.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.User
import com.example.cookitup.domain.repository.SupabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class PeopleState {
    data class Success(val users: List<User>) : PeopleState()
    data class Error(val message: String) : PeopleState()
    data object Loading : PeopleState()
    data object Idle : PeopleState() // Add idle state for when no search is performed
}

interface PeopleActions {
    fun fetchUsers(searchQuery: String)
    fun clearResults() // Add method to clear results
}

class PeopleViewModel(
    private val repository: SupabaseRepository
) : ViewModel() {
    private val _state = MutableStateFlow<PeopleState>(PeopleState.Idle) // Start with Idle instead of Loading
    val state = _state.asStateFlow()

    val action = object : PeopleActions {
        override fun fetchUsers(searchQuery: String) {
            if (searchQuery.isBlank()) {
                _state.value = PeopleState.Idle
                return
            }

            _state.value = PeopleState.Loading

            viewModelScope.launch {
                try {
                    val users: List<User> = repository.getUsers(searchQuery)
                    _state.value = PeopleState.Success(users)
                } catch (e: Exception) {
                    _state.value = PeopleState.Error("Failed to fetch users. Please try again.")
                }
            }
        }

        override fun clearResults() {
            _state.value = PeopleState.Idle
        }
    }
}
