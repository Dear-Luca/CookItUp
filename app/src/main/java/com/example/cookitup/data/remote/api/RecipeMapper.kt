package com.example.cookitup.data.remote.api

import com.example.cookitup.domain.model.Recipe

object RecipeMapper {
    fun mapToDomain(dto: RecipeDto): Recipe {
        return Recipe(
            id = dto.id.toString(),
            image = dto.imageUrl,
            title = dto.title
        )
    }
}
