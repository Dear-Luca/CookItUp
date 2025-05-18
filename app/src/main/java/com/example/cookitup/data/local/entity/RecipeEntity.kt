package com.example.cookitup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recipe")
data class RecipeEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val image: String,
    val time: String,
    val servings: Int,
    val dishTypes: String
)
