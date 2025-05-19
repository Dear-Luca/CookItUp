package com.example.cookitup.data.local.entity

import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail

object MapperEntity {
    fun mapToDomain(entity: RecipeEntity): Recipe {
        return Recipe(
            id = entity.id.toString(),
            image = entity.image,
            title = entity.name
        )
    }

    fun mapToEntity(domain: RecipeDetail): RecipeEntity {
        return RecipeEntity(
            id = domain.id.toLong(),
            name = domain.title,
            image = domain.image,
            time = domain.time.toString(),
            servings = domain.servings,
            dishTypes = domain.types.joinToString { "," }
        )
    }
}
