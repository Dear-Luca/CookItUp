package com.example.cookitup.data.repository

import com.example.cookitup.data.remote.api.SpoonacularApi
import com.example.cookitup.data.remote.dto.RecipeMapper
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.ApiRepository

class ApiRepositoryImpl(
    private val apiService: SpoonacularApi
) : ApiRepository {
    override suspend fun getRecipes(ingredients: List<String>): List<Recipe> {
        return apiService.searchRecipes(ingredients.joinToString(",")).map { RecipeMapper.mapToDomain(it) }
    }

    override suspend fun getRecipeDetail(id: String): RecipeDetail {
        return RecipeMapper.mapToDomain(apiService.getRecipeDetail(id.toInt()))
    }

    override suspend fun getRecipeInstructions(id: String): List<RecipeInstructions> {
        return apiService.getRecipeInstructions(id.toInt()).map { RecipeMapper.mapToDomain(it) }
    }
}
