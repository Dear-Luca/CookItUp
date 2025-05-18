package com.example.cookitup.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.RecipeIngredientCrossRef

@Dao
interface CrossRefDao {
    @Upsert
    suspend fun upsertCrossRef(refs: RecipeIngredientCrossRef)

    @Delete
    suspend fun deleteCrossRef(refs: RecipeIngredientCrossRef)
}
