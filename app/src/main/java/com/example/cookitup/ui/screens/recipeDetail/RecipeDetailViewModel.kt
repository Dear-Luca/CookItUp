package com.example.cookitup.ui.screens.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.repository.ApiRepository
import com.example.cookitup.domain.repository.CacheRepository
import com.example.cookitup.domain.repository.DbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class RecipeDetailState {
    data class Success(
        val detail: RecipeDetail,
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
    private val dbRepository: DbRepository,
    private val cacheRepository: CacheRepository
) : ViewModel() {
    private val _state = MutableStateFlow<RecipeDetailState>(RecipeDetailState.Loading)
    val state = _state.asStateFlow()

    val actions = object : RecipeDetailActions {
        override fun fetchRecipeDetail(id: String) {
            viewModelScope.launch {
                try {
                    val isFavourite = dbRepository.isFavourite(id).first()

                    val recipeDetail = if (isFavourite) {
                        dbRepository.getRecipeFull(id)
                    } else {
                        apiRepository.getRecipeDetail(id)
                    }

                    _state.value = RecipeDetailState.Success(
                        recipeDetail,
                        isFavourite
                    )
                    cacheRepository.cacheRecipeInstructions(recipeDetail.id, recipeDetail.instructions)
                } catch (e: Exception) {
                    _state.value = RecipeDetailState.Error(e.message ?: "Unknown")
                }
            }
        }

        override fun toggleFavourites() {
            val currentState = _state.value
            if (currentState is RecipeDetailState.Success) {
                viewModelScope.launch {
                    if (currentState.isFavourite) {
                        dbRepository.deleteRecipe(currentState.detail)
                    } else {
                        dbRepository.upsertRecipe(currentState.detail)
                        dbRepository.upsertInstructions(currentState.detail.instructions, currentState.detail.id)
                        dbRepository.upsertIngredients(currentState.detail.ingredients)
                        currentState.detail.ingredients.forEach {
                            dbRepository.upsertCrossRef(currentState.detail.id, it.id)
                        }
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
