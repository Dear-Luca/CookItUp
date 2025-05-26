package com.example.cookitup.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.repository.ApiRepository
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

    fun fetchSimilarRecipes(id: String)
}

class RecipesViewModel(
    private val repository: ApiRepository
) : ViewModel() {
    private val _state = MutableStateFlow<RecipesState>(RecipesState.Loading)
    val state = _state.asStateFlow()

    val actions = object : RecipesActions {
        override fun fetchRecipes(ingredients: List<String>) {
            viewModelScope.launch {
                _state.value = RecipesState.Loading
                try {
                    val recipes = repository.getRecipes(ingredients)
                    _state.value = RecipesState.Success(recipes)
                } catch (e: Exception) {
                    // UnknownHostException
                    // HttpException
                    // TooManyRequest 429 error
                    // http error 402
                    _state.value = RecipesState.Error(e.message ?: "Error")
                }
            }
        }

        override fun fetchSimilarRecipes(id: String) {
            viewModelScope.launch {
                _state.value = RecipesState.Loading
                try {
                    val recipeIds = repository.getSimilarRecipes(id)
                    val recipes = recipeIds.map { id ->
                        repository.getRecipeDetail(id.toString())
                    }.map { detail ->
                        Recipe(detail.id, detail.title, detail.image)
                    }
                    _state.value = RecipesState.Success(recipes)
                } catch (e: Exception) {
                    _state.value = RecipesState.Error(e.message ?: "Error")
                }
            }
        }
    }
}
