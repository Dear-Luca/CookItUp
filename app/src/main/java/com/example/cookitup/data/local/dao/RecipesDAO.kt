package com.example.cookitup.data.local.dao

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.RecipeFull
import kotlinx.coroutines.flow.Flow

interface RecipesDAO {
    @Query("SELECT * FROM RECIPE")
    fun getFavourites(): Flow<List<RecipeFull>>

    @Upsert
    suspend fun upsert(recipeFull: RecipeFull)

    @Delete
    suspend fun delete(recipeFull: RecipeFull)
}
