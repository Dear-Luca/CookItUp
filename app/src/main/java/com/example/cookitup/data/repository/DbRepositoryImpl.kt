package com.example.cookitup.data.repository

import com.example.cookitup.data.local.dao.CrossRefDao
import com.example.cookitup.data.local.dao.IngredientDao
import com.example.cookitup.data.local.dao.InstructionDao
import com.example.cookitup.data.local.dao.RecipeDao
import com.example.cookitup.data.local.dao.RecipeFullDao
import com.example.cookitup.data.local.entity.MapperEntity
import com.example.cookitup.data.local.entity.RecipeIngredientCrossRef
import com.example.cookitup.domain.model.Ingredient
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DbRepositoryImpl(
    private val recipeDAO: RecipeDao,
    private val instructionDao: InstructionDao,
    private val crossRefDao: CrossRefDao,
    private val recipeFullDao: RecipeFullDao,
    private val ingredientDao: IngredientDao
) : DbRepository {
    // RECIPE
    override fun getFavourites(): Flow<List<Recipe>> {
        return recipeDAO.getRecipes().map {
                entities ->
            entities.map { MapperEntity.mapToDomain(it) }
        }
    }

    override fun isFavourite(id: String): Flow<Boolean> {
        return recipeDAO.isFavourite(id.toLong())
    }

    override suspend fun upsertRecipe(recipe: RecipeDetail) {
        return recipeDAO.upsertRecipe(MapperEntity.mapToEntity(recipe))
    }

    override suspend fun deleteRecipe(recipe: RecipeDetail) {
        recipeDAO.deleteRecipe(MapperEntity.mapToEntity(recipe))
    }

    override suspend fun getRecipeFull(id: String): RecipeDetail {
        return MapperEntity.mapToDomain(recipeFullDao.getRecipeFullById(id))
    }

    // INSTRUCTIONS

    override suspend fun upsertInstructions(instructions: RecipeInstructions, recipeId: String) {
        instructions.steps.forEach { instruction ->
            instructionDao.upsertInstruction(MapperEntity.mapToEntity(instruction, recipeId))
        }
    }

    override suspend fun getInstructions(id: String): RecipeInstructions {
        return MapperEntity.mapToDomain(instructionDao.getRecipeInstructions(id))
    }

    // INGREDIENTS
    override suspend fun getIngredients(id: String): List<Ingredient> {
        return crossRefDao.getRecipeIngredients(id.toLong()).map {
            MapperEntity.mapToDomain(it)
        }
    }

    override suspend fun upsertIngredients(ingredients: List<Ingredient>) {
        ingredients.forEach {
            ingredientDao.upsertIngredient(MapperEntity.mapToEntity(it))
        }
    }

    override suspend fun upsertCrossRef(recipeId: String, ingredientId: String) {
        crossRefDao.upsertCrossRef(RecipeIngredientCrossRef(recipeId.toLong(), ingredientId.toLong()))
    }
}
