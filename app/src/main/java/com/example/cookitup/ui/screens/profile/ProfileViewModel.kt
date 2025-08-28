package com.example.cookitup.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.Post
import com.example.cookitup.domain.model.User
import com.example.cookitup.domain.repository.SupabaseRepository
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileState {
    data class Success(val user: User) : ProfileState()
    data object Loading : ProfileState()
    data class Error(val message: String) : ProfileState()
}

sealed class UpdateState {
    data object Idle : UpdateState()
    data object Loading : UpdateState()
    data object Success : UpdateState()
    data class Error(val message: String) : UpdateState()
}

sealed class PostsState {
    data class Success(val posts: List<Post>) : PostsState()
    data object Loading : PostsState()
    data class Error(val message: String) : PostsState()
}

sealed class ImageUpdateState {
    data object Idle : ImageUpdateState()
    data object Loading : ImageUpdateState()
    data object Success : ImageUpdateState()
    data class Error(val message: String) : ImageUpdateState()
}

interface ProfileActions {
    fun getCurrentUser()
    fun getPosts(id: String)
    fun updateUsername(newUsername: String)
    fun updatePassword(newPassword: String)
    fun clearUpdateState()
    fun deleteCurrentUser()
    fun updateProfileImage(fileName: String?, imageBytes: ByteArray)
    fun deletePost(postId: String)
    fun clearImageUpdateState()
}

class ProfileViewModel(
    private val repository: SupabaseRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = _state.asStateFlow()

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState = _updateState.asStateFlow()

    private val _postsState = MutableStateFlow<PostsState>(PostsState.Loading)
    val postsState = _postsState.asStateFlow()

    private val _imageUpdateState = MutableStateFlow<ImageUpdateState>(ImageUpdateState.Idle)
    val imageUpdateState = _imageUpdateState.asStateFlow()

    val actions = object : ProfileActions {
        override fun getCurrentUser() {
            viewModelScope.launch {
                _state.value = ProfileState.Loading
                try {
                    val user = repository.getCurrentUser()
                    _state.value = ProfileState.Success(user)
                } catch (e: Exception) {
                    _state.value = ProfileState.Error(e.message ?: "Error")
                }
            }
        }

        override fun getPosts(id: String) {
            viewModelScope.launch {
                _postsState.value = PostsState.Loading
                try {
                    val posts = repository.getPosts(id)
                    _postsState.value = PostsState.Success(posts)
                } catch (e: Exception) {
                    _postsState.value = PostsState.Error("Failed to load posts: ${e.message}")
                }
            }
        }

        override fun updateProfileImage(fileName: String?, imageBytes: ByteArray) {
            viewModelScope.launch {
                _imageUpdateState.value = ImageUpdateState.Loading
                try {
                    if (fileName != null) {
                        // Update the image in the repository
                        repository.updateProfileImage(fileName, imageBytes)

                        // Refresh the user data to get the updated image path
                        val updatedUser = repository.getCurrentUser()
                        _state.value = ProfileState.Success(updatedUser)
                        _imageUpdateState.value = ImageUpdateState.Success
                    }
                } catch (e: Exception) {
                    _imageUpdateState.value = ImageUpdateState.Error(e.message ?: "Failed to update profile image")
                }
            }
        }

        override fun deletePost(postId: String) {
            viewModelScope.launch {
                // First, remove the post locally for immediate UI update
                val currentState = _postsState.value
                if (currentState is PostsState.Success) {
                    val updatedPosts = currentState.posts.filter { it.id != postId }
                    _postsState.value = PostsState.Success(updatedPosts)
                }

                // Then delete from backend
                repository.deletePost(postId)
            }
        }

        override fun updateUsername(newUsername: String) {
            viewModelScope.launch {
                _updateState.value = UpdateState.Loading
                try {
                    val isAvailable = repository.checkUsername(newUsername)
                    if (isAvailable) {
                        repository.updateUsername(newUsername)
                        val updatedUser = repository.getCurrentUser()
                        _state.value = ProfileState.Success(updatedUser)
                        _updateState.value = UpdateState.Success
                    } else {
                        _updateState.value = UpdateState.Error("$newUsername already taken")
                    }
                } catch (e: Exception) {
                    _state.value = ProfileState.Error(e.message ?: "Error")
                }
            }
        }

        override fun updatePassword(newPassword: String) {
            viewModelScope.launch {
                try {
                    repository.updatePassword(newPassword)
                    _updateState.value = UpdateState.Success
                } catch (e: RestException) {
                    when (e.error) {
                        "same_password" -> _updateState.value = UpdateState.Error(
                            "new Password should be different from the previous one"
                        )
                    }
                } catch (e: Exception) {
                    _updateState.value = UpdateState.Error("Error")
                }
            }
        }

        override fun deleteCurrentUser() {
            viewModelScope.launch {
                try {
                    repository.deleteCurrentUser()
                    _updateState.value = UpdateState.Success
                } catch (e: Exception) {
                    _state.value = ProfileState.Error("An error occurred")
                }
            }
        }

        override fun clearUpdateState() {
            _updateState.value = UpdateState.Idle
        }

        override fun clearImageUpdateState() {
            _imageUpdateState.value = ImageUpdateState.Idle
        }
    }
}
