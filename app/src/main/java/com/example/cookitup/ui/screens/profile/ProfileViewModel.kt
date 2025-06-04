package com.example.cookitup.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.data.remote.supabase.Supabase
import com.example.cookitup.domain.model.User
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileState {
    data class Success(val user: User) : ProfileState()
    data object Loading : ProfileState()
    data class Error(val message: String) : ProfileState()
}

interface ProfileActions {
    fun getCurrentUser()
}

class ProfileViewModel(
    private val client: SupabaseClient = Supabase.client
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState.Loading)
    val state = _state.asStateFlow()

    val actions = object : ProfileActions {
        override fun getCurrentUser() {
            viewModelScope.launch {
//                val currentUser = client.auth.retrieveUserForCurrentSession()
//                val user = client.from("users").select(
//
//                )
//
//                val res = User(
//                    id = user.id,
//                    email = user.email ?: "",
//                    username = userMetadata?.get("username")!!.toString(),
//                    image = userMetadata
//                )
            }
        }
    }
}
