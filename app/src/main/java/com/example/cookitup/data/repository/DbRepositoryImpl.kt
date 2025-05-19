package com.example.cookitup.data.repository

import com.example.cookitup.data.local.dao.RecipeDao
import com.example.cookitup.data.local.entity.MapperEntity
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.repository.DbRepository

class DbRepositoryImpl(
    private val recipeDAO: RecipeDao
) : DbRepository {
    override suspend fun getRecipes(): List<Recipe> {
        return recipeDAO.getRecipes().map { MapperEntity.mapToDomain(it) }
    }

    override suspend fun getRecipeDetail(): RecipeDetail {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeInstructions() {
        TODO("Not yet implemented")
    }
}
