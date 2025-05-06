package com.example.cookitup.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeDetailDto(
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("readyInMinutes") val readyInMinutes: Int,
    @SerializedName("servings") val servings: Int,
    @SerializedName("dishTypes") val dishTypes: List<String>,
    @SerializedName("extendedIngredients") val extendedIngredients: List<IngredientDto>
)
