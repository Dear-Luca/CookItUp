package com.example.cookitup.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.RecipeEntity
import com.example.cookitup.data.local.entity.RecipeFull
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("Select id, image, name  FROM recipe")
    suspend fun getRecipes(): List<RecipeEntity>

    @Upsert
    suspend fun upsertRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}

@Dao
interface RecipeFullDao {
    @Transaction
    @Query("SELECT * FROM recipe")
    fun getFavourites(): Flow<List<RecipeFull>>
}
