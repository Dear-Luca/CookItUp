package com.example.cookitup.ui.screens.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.ApiRepository
import com.example.cookitup.domain.repository.DbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class RecipeDetailState {
    data class Success(
        val detail: RecipeDetail,
        val instructions: RecipeInstructions,
        val isFavourite: Boolean
    ) : RecipeDetailState()
    data object Loading : RecipeDetailState()
    data class Error(val message: String) : RecipeDetailState()
}

interface RecipeDetailActions {

    fun fetchRecipeDetail(id: String)

    fun toggleFavourites()

    fun observeFavourite(id: String)
}

class RecipeDetailViewModel(
    private val apiRepository: ApiRepository,
    private val dbRepository: DbRepository
) : ViewModel() {
    private val _state = MutableStateFlow<RecipeDetailState>(RecipeDetailState.Loading)
    val state = _state.asStateFlow()

    val actions = object : RecipeDetailActions {
        override fun fetchRecipeDetail(id: String) {
            viewModelScope.launch {
                try {
                    val recipeDetail = apiRepository.getRecipeDetail(id)
                    val recipeInstructions = apiRepository.getRecipeInstructions(id)
                    val isFavouriteInitial = dbRepository.isFavourite(id).first()
                    _state.value = RecipeDetailState.Success(
                        recipeDetail,
                        recipeInstructions.first(),
                        isFavouriteInitial
                    )
                } catch (e: Exception) {
                    // UnknownHostException
                    // HttpException
                    _state.value = RecipeDetailState.Error(e.message ?: "Error")
                }
            }
        }

        override fun toggleFavourites() {
            val currentState = _state.value
            if (currentState is RecipeDetailState.Success) {
                viewModelScope.launch {
                    try {
                        if (currentState.isFavourite) {
                            dbRepository.deleteRecipe(currentState.detail)
                        } else {
                            dbRepository.upsertRecipe(currentState.detail)
                        }
                    } catch (e: Exception) {
                        _state.value = RecipeDetailState.Error(e.message ?: "Error")
                    }
                }
            }
        }

        override fun observeFavourite(id: String) {
            viewModelScope.launch {
                dbRepository
                    .isFavourite(id)
                    .collect { favourite ->
                        val current = _state.value
                        if (current is RecipeDetailState.Success) {
                            _state.value = current.copy(isFavourite = favourite)
                        }
                    }
            }
        }
    }
}
