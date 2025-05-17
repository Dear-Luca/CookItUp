package com.example.cookitup.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cookitup.data.local.entity.RecipeEntity
import com.example.cookitup.data.local.entity.RecipeIngredientCrossRef
import com.example.cookitup.data.local.entity.RecipeIngredientEntity
import com.example.cookitup.data.local.entity.RecipeInstructionEntity
import com.example.cookitup.domain.model.RecipeInstructions

@Database(
    entities = [
        RecipeEntity::class,
        RecipeIngredientEntity::class,
        RecipeInstructionEntity::class,
        RecipeIngredientCrossRef::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeEntityDao(): RecipeEntity
    abstract fun recipeIngredientEntityDao(): RecipeIngredientEntity
    abstract fun recipeInstructionsEntity(): RecipeInstructions
    abstract fun recipeIngredientCrossRef(): RecipeIngredientCrossRef

    companion object {
        const val DB_NAME = "CookItUpDB"
    }
}
