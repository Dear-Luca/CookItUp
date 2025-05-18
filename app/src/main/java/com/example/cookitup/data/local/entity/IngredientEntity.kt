package com.example.cookitup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ingredient")
data class IngredientEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val amount: Double,
    val unit: String
)
