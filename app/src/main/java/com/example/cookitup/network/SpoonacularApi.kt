package com.example.cookitup.network

import com.example.cookitup.BuildConfig
import com.example.cookitup.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = BuildConfig.API_KEY

interface SpoonacularApi {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): RecipeResponse
}
