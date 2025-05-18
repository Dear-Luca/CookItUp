package com.example.cookitup.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    "recipeIngredientCrossRef",
    primaryKeys = ["recipeId", "ingredientId"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = IngredientEntity::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeIngredientCrossRef(
    val recipeId: Long,
    val ingredientId: Long
)
