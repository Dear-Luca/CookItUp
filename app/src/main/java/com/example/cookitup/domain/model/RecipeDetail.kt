package com.example.cookitup.domain.model

data class RecipeDetail(
    val title: String,
    val image: String,
    val time: String,
    val servings: Int,
    val types: List<String>,
    val ingredients: List<Ingredient>,
    val instructions: RecipeInstructions,
    val id: String
)
