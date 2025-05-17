package com.example.cookitup.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.RecipeEntity
import com.example.cookitup.data.local.entity.RecipeFull
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Upsert
    suspend fun upsertRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}

@Dao
interface RecipeFullDao {
    @Query("SELECT * FROM recipe")
    fun getFavourites(): Flow<List<RecipeFull>>
}
