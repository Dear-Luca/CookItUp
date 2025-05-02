package com.example.cookitup.network

import com.example.cookitup.BuildConfig
import com.example.cookitup.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Query



interface SpoonacularApi {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipes(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = NUM_ITEMS,
        @Query("apiKey") apiKey: String = API_KEY
    ): List<Recipe>
}
