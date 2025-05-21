package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.Ingredient
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    // RECIPE
    fun getFavourites(): Flow<List<Recipe>>

//    suspend fun getFavouriteDetail(id: String, ingredients: List<Ingredient>) : RecipeDetail

    fun isFavourite(id: String): Flow<Boolean>

    suspend fun upsertRecipe(recipe: RecipeDetail)

    suspend fun deleteRecipe(recipe: RecipeDetail)

    // ADD FULL RECIPE!!
    suspend fun getRecipeFull(id: String): RecipeDetail

    // INSTRUCTION
    suspend fun upsertInstructions(instructions: RecipeInstructions, recipeId: String)
    suspend fun getInstructions(id: String): RecipeInstructions

    // INGREDIENTS
    suspend fun getIngredients(id: String): List<Ingredient>
    suspend fun upsertIngredients(ingredients: List<Ingredient>)

    // CROSS REF
    suspend fun upsertCrossRef(recipeId: String, ingredientId: String)
}
