package com.example.cookitup.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.model.Recipe
import com.example.cookitup.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RecipesState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)

interface RecipesActions {
    fun fetchRecipes(ingredients: List<String>)
}

class RecipesViewModel : ViewModel() {
    private val _state = MutableStateFlow(RecipesState())
    val state = _state.asStateFlow()

    val actions = object : RecipesActions {
        override fun fetchRecipes(ingredients: List<String>) {
            viewModelScope.launch {
                _state.value = _state.value.copy(
                    isLoading = true
                )

                try {
                    val recipes = getRecipes(ingredients)
                    _state.value = _state.value.copy(
                        recipes = recipes,
                        isLoading = false
                    )
                } catch (e: Exception) {
                    // UnknownHostException
                    // HttpException
                    e.printStackTrace()
                }
            }
        }
    }
}

private suspend fun getRecipes(ingredients: List<String>): List<Recipe> {
    val ingredientsList = ingredients.joinToString(",")
    return ApiClient.retrofitService
        .searchRecipes(ingredientsList)
}
