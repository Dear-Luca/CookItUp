package com.example.cookitup.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.IngredientEntity
import com.example.cookitup.data.local.entity.RecipeIngredientCrossRef

@Dao
interface CrossRefDao {
    @Upsert
    suspend fun upsertCrossRef(refs: RecipeIngredientCrossRef)

    @Delete
    suspend fun deleteCrossRef(refs: RecipeIngredientCrossRef)

    @Query(
        "SELECT i.name, i.amount, i.unit, i.id " +
            "FROM ingredient i " +
            "INNER JOIN RecipeIngredientCrossRef ric ON i.id = ric.ingredientId " +
            "WHERE ric.recipeId = :recipeId"
    )
    suspend fun getRecipeIngredients(recipeId: Long): List<IngredientEntity>
}
