package com.example.cookitup.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.IngredientEntity

@Dao
interface IngredientDao {
    @Upsert
    suspend fun upsertIngredient(ingredient: IngredientEntity)

    @Upsert
    suspend fun deleteIngredient(ingredient: IngredientEntity)
}
