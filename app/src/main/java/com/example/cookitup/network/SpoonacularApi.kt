package com.example.cookitup.network

import retrofit2.http.GET
import retrofit2.http.Query

const val api = "API_KEY"

interface SpoonacularApi {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = api
    ): RecipeResponse
}
