package com.example.cookitup.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RecipesState {
    data class Success(val recipes: List<Recipe>) : RecipesState()
    data object Loading : RecipesState()
    data class Error(val message: String) : RecipesState()
}

interface RecipesActions {
    fun fetchRecipes(ingredients: List<String>)
}

class RecipesViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _state = MutableStateFlow<RecipesState>(RecipesState.Loading)
    val state = _state.asStateFlow()

    val actions = object : RecipesActions {
        override fun fetchRecipes(ingredients: List<String>) {
            viewModelScope.launch {
                try {
                    val recipes = repository.fetchRecipes(ingredients)
                    _state.value = RecipesState.Success(recipes)
                } catch (e: Exception) {
                    // UnknownHostException
                    // HttpException
                    _state.value = RecipesState.Error(e.message ?: "Error")
                }
            }
        }
    }
}
