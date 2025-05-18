package com.example.cookitup.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    "instruction",
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
data class InstructionEntity(
    val recipeId: Long,
    val number: Int,
    val instruction: String
)
