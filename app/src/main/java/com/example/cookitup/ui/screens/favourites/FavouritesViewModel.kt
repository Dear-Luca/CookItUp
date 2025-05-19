package com.example.cookitup.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.repository.DbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class FavouritesState {
    data class Success(val recipes: List<Recipe>) : FavouritesState()
    data object Loading : FavouritesState()
    data class Error(val message: String) : FavouritesState()
}

interface FavouritesActions {
    fun fetchRecipes()
}

class FavouritesViewModel(
    private val repository: DbRepository
) : ViewModel() {
    private val _state = MutableStateFlow<FavouritesState>(FavouritesState.Loading)
    val state = _state.asStateFlow()

    val actions = object : FavouritesActions {
        override fun fetchRecipes() {
            viewModelScope.launch {
                repository.getRecipes()
                    .onStart {
                        _state.value = FavouritesState.Loading
                    }
                    .catch { e ->
                        _state.value = FavouritesState.Error(e.message ?: "Error")
                    }
                    .collect { recipes ->
                        _state.value = FavouritesState.Success(recipes)
                    }
            }
        }
    }
}
