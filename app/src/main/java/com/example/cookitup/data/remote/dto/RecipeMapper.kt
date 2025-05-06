package com.example.cookitup.data.remote.dto

import com.example.cookitup.domain.model.Ingredient
import com.example.cookitup.domain.model.MeasureUnit
import com.example.cookitup.domain.model.Measures
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

    fun mapToDomain(dto: RecipeDetailDto): RecipeDetail {
        return RecipeDetail(
            title = dto.title,
            image = dto.imageUrl,
            time = dto.readyInMinutes,
            servings = dto.servings,
            types = dto.dishTypes
        )
    }

    fun mapToDomain(dto: IngredientDto): Ingredient {
        return Ingredient(
            id = dto.id.toString(),
            image = dto.imageUrl,
            name = dto.name,
            measures = mapToDomain(dto.measures)
        )
    }

    private fun mapToDomain(dto: MeasureUnitDto): MeasureUnit {
        return MeasureUnit(
            amount = dto.amount.toString(),
            unit = dto.unitShort
        )
    }

    private fun mapToDomain(dto: MeasuresDto): Measures {
        return Measures(
            metric = mapToDomain(dto.metric)
        )
    }
}
