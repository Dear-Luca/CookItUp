package com.example.cookitup.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cookitup.data.local.dao.CrossRefDao
import com.example.cookitup.data.local.dao.IngredientDao
import com.example.cookitup.data.local.dao.InstructionDao
import com.example.cookitup.data.local.dao.RecipeDao
import com.example.cookitup.data.local.dao.RecipeFullDao
import com.example.cookitup.data.local.entity.IngredientEntity
import com.example.cookitup.data.local.entity.InstructionEntity
import com.example.cookitup.data.local.entity.RecipeEntity
import com.example.cookitup.data.local.entity.RecipeIngredientCrossRef

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        InstructionEntity::class,
        RecipeIngredientCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeEntityDao(): RecipeDao
    abstract fun recipeFullEntityDao(): RecipeFullDao
    abstract fun ingredientEntityDao(): IngredientDao
    abstract fun instructionEntityDao(): InstructionDao
    abstract fun recipeIngredientCrossRefDao(): CrossRefDao

    companion object {
        const val DB_NAME = "CookItUpDB"
    }
}
