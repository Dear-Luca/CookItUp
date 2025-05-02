package com.example.cookitup.data.repository

import com.example.cookitup.data.remote.api.RecipeMapper
import com.example.cookitup.data.remote.api.SpoonacularApi
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val apiService: SpoonacularApi
) : RecipeRepository {
    override suspend fun fetchRecipes(ingredients: List<String>): List<Recipe> {
        return apiService.searchRecipes(ingredients.joinToString(",")).map { RecipeMapper.mapToDomain(it) }
    }
}
