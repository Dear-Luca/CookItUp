package com.example.cookitup.ui.screens.cookRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.CacheRepository
import com.example.cookitup.domain.repository.SupabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CookRecipeState {
    data object Success : CookRecipeState()
    data object Loading : CookRecipeState()
    data class Error(val message: String) : CookRecipeState()
}

interface CookRecipeActions {
    fun getInstructions(id: String): RecipeInstructions?

    fun saveRecipeImage(fileName: String?, imageBytes: ByteArray, recipeId: String)
}

class CookRecipeViewModel(
    private val supabaseRepository: SupabaseRepository,
    private val cacheRepository: CacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CookRecipeState>(CookRecipeState.Loading)
    val state = _state.asStateFlow()

    val actions = object : CookRecipeActions {
        override fun getInstructions(id: String): RecipeInstructions? {
            return cacheRepository.getRecipeInstructions(id)
        }

        override fun saveRecipeImage(fileName: String?, imageBytes: ByteArray, recipeId: String) {
            viewModelScope.launch {
                try {
                    if (fileName != null) {
                        // Update the image in the repository
                        supabaseRepository.insertRecipePost(fileName, imageBytes, recipeId)

                        // Refresh the user data to get the updated image path
                        val updatedUser = supabaseRepository.getCurrentUser()
                        _state.value = CookRecipeState.Success
                    }
                } catch (e: Exception) {
                    _state.value = CookRecipeState.Error(e.message ?: "An error occurred")
                }
            }
        }
    }
}
