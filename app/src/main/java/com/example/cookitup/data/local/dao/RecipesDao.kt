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
    @Query("SELECT * FROM RECIPE")
    fun getRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM RECIPE WHERE id = :id")
    suspend fun getRecipe(id: Long): RecipeEntity

    @Query("SELECT EXISTS(SELECT 1 FROM recipe WHERE id = :id)")
    fun isFavourite(id: Long): Flow<Boolean>

    @Upsert
    suspend fun upsertRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}

@Dao
interface RecipeFullDao {
    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeFullById(id: String): RecipeFull
}
