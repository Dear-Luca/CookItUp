package com.example.cookitup.network

import com.example.cookitup.BuildConfig
import com.example.cookitup.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = BuildConfig.API_KEY

interface SpoonacularApi {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipes(
        @Query("ingredients") ingredients: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): List<Recipe>
}
