package com.example.cookitup.domain.model

data class RecipeDetail(
    val title: String,
    val image: String,
    val time: Int,
    val servings: Int,
    val types: List<String>,
    val ingredients: List<Ingredient>
)
