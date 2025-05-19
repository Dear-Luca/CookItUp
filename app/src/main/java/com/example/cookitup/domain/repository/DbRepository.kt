package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail

interface DbRepository {
    suspend fun getRecipes(): List<Recipe>

    suspend fun getRecipeDetail(): RecipeDetail

    suspend fun getRecipeInstructions()
}
