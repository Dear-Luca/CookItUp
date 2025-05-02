package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.Recipe

interface RecipeRepository {
    suspend fun fetchRecipes(ingredients: List<String>): List<Recipe>
}
