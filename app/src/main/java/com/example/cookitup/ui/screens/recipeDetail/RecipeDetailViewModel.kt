package com.example.cookitup.ui.screens.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RecipeDetailState {
    data class Success(val detail: RecipeDetail, val instructions: RecipeInstructions) : RecipeDetailState()
    data object Loading : RecipeDetailState()
    data class Error(val message: String) : RecipeDetailState()
}

interface RecipeDetailActions {
    fun fetchRecipeDetail(id: String)
}

class RecipeDetailViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _state = MutableStateFlow<RecipeDetailState>(RecipeDetailState.Loading)
    val state = _state.asStateFlow()

    val actions = object : RecipeDetailActions {
        override fun fetchRecipeDetail(id: String) {
            viewModelScope.launch {
                try {
                    val recipeDetail = repository.getRecipeDetail(id)
                    val recipeInstructions = repository.getRecipeInstructions(id)
                    _state.value = RecipeDetailState.Success(recipeDetail, recipeInstructions.first())
                } catch (e: Exception) {
                    // UnknownHostException
                    // HttpException
                    _state.value = RecipeDetailState.Error(e.message ?: "Error")
                }
            }
        }
    }
}
