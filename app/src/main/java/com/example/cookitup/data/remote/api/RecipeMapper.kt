package com.example.cookitup.data.remote.api

import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail

object RecipeMapper {
    fun mapToDomain(dto: RecipeDto): Recipe {
        return Recipe(
            id = dto.id.toString(),
            image = dto.imageUrl,
            title = dto.title
        )
    }

    fun mapToDomain(dto: RecipeDetailDto) : RecipeDetail{
        return RecipeDetail(
            title = dto.title,
            image = dto.imageUrl,
            time = dto.readyInMinutes,
            servings = dto.servings,
        )
    }
}
