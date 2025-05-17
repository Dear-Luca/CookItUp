package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions

interface ApiRepository {
    suspend fun getRecipes(ingredients: List<String>): List<Recipe>

    suspend fun getRecipeDetail(id: String): RecipeDetail

    suspend fun getRecipeInstructions(id: String): List<RecipeInstructions>
}
