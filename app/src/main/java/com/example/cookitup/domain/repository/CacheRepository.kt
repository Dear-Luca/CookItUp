package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.RecipeInstructions

interface CacheRepository {
    fun cacheRecipeInstructions(id: String, instructions: RecipeInstructions)

    fun getRecipeInstructions(id: String): RecipeInstructions?
}
