package com.example.cookitup.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApi {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipes(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = NUM_ITEMS,
        @Query("apiKey") apiKey: String = API_KEY
    ): List<RecipeDto>

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetail(
        @Path("id") id : Int,
        @Query("includeNutrition") nutrition : Boolean = false,
        @Query("apiKey") apiKey: String = API_KEY
    ) : RecipeDetailDto

}
