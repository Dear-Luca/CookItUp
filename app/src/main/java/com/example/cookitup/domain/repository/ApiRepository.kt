package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail

interface ApiRepository {
    suspend fun getRecipes(ingredients: List<String>): List<Recipe>

    suspend fun getRecipeDetail(id: String): RecipeDetail

    suspend fun getSimilarRecipes(id: String): List<Recipe>
}
