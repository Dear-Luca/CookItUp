package com.example.cookitup.ui.screens.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RecipeDetailState {
    data class Success(val detail: RecipeDetail) : RecipeDetailState()
    data object Loading : RecipeDetailState()
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
                    _state.value = RecipeDetailState.Success(recipeDetail)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
