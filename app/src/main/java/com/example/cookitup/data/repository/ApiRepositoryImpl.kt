package com.example.cookitup.data.repository

import com.example.cookitup.data.remote.api.SpoonacularApi
import com.example.cookitup.data.remote.dto.MapperDto
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.ApiRepository

class ApiRepositoryImpl(
    private val apiService: SpoonacularApi
) : ApiRepository {
    override suspend fun getRecipes(ingredients: List<String>): List<Recipe> {
        return apiService.searchRecipes(ingredients.joinToString(",")).map { MapperDto.mapToDomain(it) }
    }

    override suspend fun getRecipeDetail(id: String): RecipeDetail {
        val instructions = apiService.getRecipeInstructions(id.toInt()).map { MapperDto.mapToDomain(it) }
        return if (instructions.isEmpty()) {
            MapperDto.mapToDomain(apiService.getRecipeDetail(id.toInt()), RecipeInstructions(emptyList()))
        } else {
            MapperDto.mapToDomain(apiService.getRecipeDetail(id.toInt()), instructions[0])
        }
    }

    override suspend fun getSimilarRecipes(id: String): List<Recipe> {
        val recipes = apiService.getSimilarRecipes(id.toInt()).map { MapperDto.mapToDomain(it) }
        return recipes
    }
}
