package com.example.cookitup.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.data.remote.supabase.Supabase
import com.example.cookitup.domain.repository.SupabaseRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    data object Initializing : AuthState()
    data object Loading : AuthState()
    data class Authenticated(val session: UserSession) : AuthState()
    data object NotAuthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

interface AuthActions {
    fun signInUser(email: String, password: String)
    fun signUpUser(email: String, password: String, username: String)
    fun signOutUser()
    fun clearError()
}

class AuthViewModel(
    private val repository: SupabaseRepository,
    private val client: SupabaseClient = Supabase.client
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Initializing)
    val state = _state.asStateFlow()

    init {
        observeSessionStatus()
    }

    private fun observeSessionStatus() {
        viewModelScope.launch {
            client.auth.sessionStatus.collect { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        val session = status.session
                        _state.value = AuthState.Authenticated(session)
                    }
                    SessionStatus.Initializing -> {
                        if (_state.value !is AuthState.Loading && _state.value !is AuthState.Error) {
                            _state.value = AuthState.Initializing
                        }
                    }
                    is SessionStatus.NotAuthenticated -> {
                        if (_state.value !is AuthState.Loading) {
                            _state.value = AuthState.NotAuthenticated
                        }
                    }
                    is SessionStatus.RefreshFailure -> {
                        _state.value = AuthState.Error(status.cause.toString())
                    }
                }
            }
        }
    }

    val actions = object : AuthActions {
        override fun signInUser(email: String, password: String) {
            viewModelScope.launch {
                _state.value = AuthState.Loading
                try {
                    repository.signIn(email, password)
                } catch (e: Exception) {
                    _state.value = AuthState.Error("Invalid credentials")
                }
            }
        }

        override fun signUpUser(email: String, password: String, username: String) {
            viewModelScope.launch {
                _state.value = AuthState.Loading
                try {
                    val isAvailable = repository.checkEmail(email)
                    if (isAvailable) {
                        repository.signUp(email, password, username)
                    } else {
                        _state.value = AuthState.Error("Email already registered")
                    }
                } catch (e: Exception) {
                    _state.value = AuthState.Error("An error occurred")
                }
            }
        }

        override fun signOutUser() {
            viewModelScope.launch {
                try {
                    repository.signOut()
                } catch (e: Exception) {
                    _state.value = AuthState.NotAuthenticated
                }
            }
        }

        override fun clearError() {
            if (_state.value is AuthState.Error) {
                _state.value = AuthState.NotAuthenticated
            }
        }
    }
}
