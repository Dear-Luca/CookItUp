package com.example.cookitup.data.repository

import com.example.cookitup.data.local.dao.RecipeDao
import com.example.cookitup.data.local.entity.MapperEntity
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DbRepositoryImpl(
    private val recipeDAO: RecipeDao
) : DbRepository {
    override fun getRecipes(): Flow<List<Recipe>> {
        return recipeDAO.getRecipes().map {
                entities ->
            entities.map { MapperEntity.mapToDomain(it) }
        }
    }

    override fun isFavourite(id: String): Flow<Boolean> {
        return recipeDAO.isFavourite(id.toLong())
    }

    override suspend fun getRecipeDetail(): RecipeDetail {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeInstructions() {
        TODO("Not yet implemented")
    }

    override suspend fun upsertRecipe(recipe: RecipeDetail) {
        return recipeDAO.upsertRecipe(MapperEntity.mapToEntity(recipe))
    }

    override suspend fun deleteRecipe(recipe: RecipeDetail) {
        recipeDAO.deleteRecipe(MapperEntity.mapToEntity(recipe))
    }
}
