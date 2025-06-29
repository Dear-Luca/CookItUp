package com.example.cookitup.ui.screens.cookRecipe

import androidx.lifecycle.ViewModel
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.CacheRepository

interface CookRecipeActions {
    fun getInstructions(id: String): RecipeInstructions
}

class CookRecipeViewModel(
    private val cacheRepository: CacheRepository
) : ViewModel() {

    val actions = object : CookRecipeActions {
        override fun getInstructions(id: String): RecipeInstructions {
            return cacheRepository.getRecipeInstructions(id)
        }
    }
}
