package com.example.cookitup.data.remote.api

import com.example.cookitup.data.remote.NUM_ITEMS
import com.example.cookitup.data.remote.SPOONACULAR_API_KEY
import com.example.cookitup.data.remote.dto.RecipeDetailDto
import com.example.cookitup.data.remote.dto.RecipeDto
import com.example.cookitup.data.remote.dto.RecipeInstructionsDto
import com.example.cookitup.data.remote.dto.SimilarRecipesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApi {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipes(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = NUM_ITEMS,
        @Query("ranking") ranking: Int = 2,
        @Query("ignorePantry") ignorePantry: Boolean = true,
        @Query("apiKey") apiKey: String = SPOONACULAR_API_KEY
    ): List<RecipeDto>

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetail(
        @Path("id") id: Int,
        @Query("includeNutrition") nutrition: Boolean = false,
        @Query("apiKey") apiKey: String = SPOONACULAR_API_KEY
    ): RecipeDetailDto

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getRecipeInstructions(
        @Path("id") id: Int,
        @Query("stepBreakdown") stepBreakdown: Boolean = true,
        @Query("apiKey") apiKey: String = SPOONACULAR_API_KEY
    ): List<RecipeInstructionsDto>

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipes(
        @Path("id") id: Int,
        @Query("number") number: Int = NUM_ITEMS,
        @Query("apiKey") apiKey: String = SPOONACULAR_API_KEY
    ): List<SimilarRecipesDto>
}
