package com.example.cookitup.domain.model

data class Post(
    val id: String,
    val image: String,
    val recipeId: String,
    val userId: String,
    val title: String,
)
