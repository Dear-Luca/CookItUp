package com.example.cookitup.data.repository

import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.CacheRepository

class CacheRepositoryImpl : CacheRepository {
    private val instructionCache = mutableMapOf<String, RecipeInstructions>()
    override fun cacheRecipeInstructions(id: String, instructions: RecipeInstructions) {
        instructionCache[id] = instructions
    }

    override fun getRecipeInstructions(id: String): RecipeInstructions {
        return instructionCache[id]!!
    }
}
