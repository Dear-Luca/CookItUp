package com.example.cookitup.data.local.entity

import com.example.cookitup.domain.model.Recipe

object MapperEntity {
    fun mapToDomain(entity: RecipeEntity): Recipe {
        return Recipe(
            id = entity.id.toString(),
            image = entity.image,
            title = entity.name
        )
    }
}
