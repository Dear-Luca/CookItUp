package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail

interface RecipeRepository {
    suspend fun fetchRecipes(ingredients: List<String>): List<Recipe>

    suspend fun getRecipeDetail(id : String) : RecipeDetail
}
