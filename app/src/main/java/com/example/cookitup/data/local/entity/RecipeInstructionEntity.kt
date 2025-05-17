package com.example.cookitup.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    "recipeInstruction",
    primaryKeys = ["recipeId", "number"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeInstructionEntity(
    val recipeId: Long,
    val number: Int,
    val instruction: String
)
