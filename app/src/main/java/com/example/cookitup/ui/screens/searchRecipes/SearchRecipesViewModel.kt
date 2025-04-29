package com.example.cookitup.ui.screens.searchRecipes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SearchRecipesState(val ingredients : List<String> = emptyList())

interface SearchRecipesActions{
    fun addIngredient(ingredient : String)
}

class SearchRecipesViewModel : ViewModel() {
    private val _state = MutableStateFlow(SearchRecipesState())

    val state = _state.asStateFlow()

    val actions = object : SearchRecipesActions {
        override fun addIngredient(ingredient: String) {
            _state.update { currentState ->
                currentState.copy(
                    ingredients = currentState.ingredients + ingredient
                )
            }
        }
    }
}

