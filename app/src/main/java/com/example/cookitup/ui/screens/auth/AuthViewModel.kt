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

//    data object Loading : AuthState()
    data class Authenticated(val session: UserSession) : AuthState()
    data object NotAuthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

interface AuthActions {
    fun singInUser(email: String, password: String)
    fun singUpUser(email: String, password: String, username: String)
    fun singOutUser()
}

class AuthViewModel(
    private val repository: SupabaseRepository,
    private val client: SupabaseClient = Supabase.client
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.NotAuthenticated)
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
                    SessionStatus.Initializing -> _state.value = AuthState.Initializing
                    is SessionStatus.NotAuthenticated -> _state.value = AuthState.NotAuthenticated
                    is SessionStatus.RefreshFailure -> _state.value = AuthState.Error("Refresh failed: ${status.cause}")
                }
            }
        }
    }

    val actions = object : AuthActions {
        override fun singInUser(email: String, password: String) {
//            _state.value = AuthState.Loading
            viewModelScope.launch {
                try {
                    repository.signIn(email, password)
                }
                // Invalid email address: AuthRestException
                // Weak password: AuthWeakPasswordException
                catch (e: Exception) {
                    _state.value = AuthState.Error(e.message ?: "Error")
                }
            }
        }

        override fun singUpUser(email: String, password: String, username: String) {
//            _state.value = AuthState.Loading
            viewModelScope.launch {
                repository.signUp(email, password, username)
                try {
                    repository.signUp(email, password, username)
                } catch (e: Exception) {
                    _state.value = AuthState.Error(e.message ?: "Error")
                }
            }
        }

        override fun singOutUser() {
            viewModelScope.launch {
                repository.signOut()
            }
        }
    }
}
