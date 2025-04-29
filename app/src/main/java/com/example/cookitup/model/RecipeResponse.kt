package com.example.cookitup.model

import kotlinx.serialization.Serializable

data class RecipeResponse(
    val results: List<Recipe>
)

@Serializable
data class Recipe(
    val id: Int,
    val title: String,
    val image: String
)
