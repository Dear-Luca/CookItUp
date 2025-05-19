package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    fun getRecipes(): Flow<List<Recipe>>

    fun isFavourite(id: String): Flow<Boolean>

    suspend fun getRecipeDetail(): RecipeDetail

    suspend fun getRecipeInstructions()

    suspend fun upsertRecipe(recipe: RecipeDetail)

    suspend fun deleteRecipe(recipe: RecipeDetail)
}
