package com.example.cookitup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recipeIngredient")
data class RecipeIngredientEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val amount: Double,
    val unit: String
)
