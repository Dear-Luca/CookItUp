package com.example.cookitup.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.data.remote.dto.MapperDto
import com.example.cookitup.data.remote.dto.UserDto
import com.example.cookitup.data.remote.supabase.Supabase
import com.example.cookitup.domain.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
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
    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = _state.asStateFlow()

    val actions = object : ProfileActions {
        override fun getCurrentUser() {
            viewModelScope.launch {
                try {
                    val currentUser = client.auth.retrieveUserForCurrentSession()
                    val userDto = client.from("users").select() {
                        filter {
                            eq("id", currentUser.id)
                        }
                    }.decodeSingle<UserDto>()
                    val user = MapperDto.mapToDomain(userDto, currentUser.email)
                    _state.value = ProfileState.Success(user)
                } catch (e: Exception) {
                    _state.value = ProfileState.Error(e.message ?: "Error")
                }
            }
        }
    }
}
