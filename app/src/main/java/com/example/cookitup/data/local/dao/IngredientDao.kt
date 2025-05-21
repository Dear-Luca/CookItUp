package com.example.cookitup.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.IngredientEntity

@Dao
interface IngredientDao {
    @Upsert
    suspend fun upsertIngredient(ingredient: IngredientEntity)

    @Delete
    suspend fun deleteIngredient(ingredient: IngredientEntity)
}
