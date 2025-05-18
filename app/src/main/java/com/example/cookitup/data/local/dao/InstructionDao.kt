package com.example.cookitup.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.example.cookitup.data.local.entity.InstructionEntity

@Dao
interface InstructionDao {
    @Upsert
    suspend fun upsertInstruction(instruction: InstructionEntity)

    @Delete
    suspend fun deleteInstruction(instruction: InstructionEntity)
}
