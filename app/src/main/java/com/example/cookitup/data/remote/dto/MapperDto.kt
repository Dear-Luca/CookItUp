package com.example.cookitup.data.remote.dto

import com.example.cookitup.domain.model.Ingredient
import com.example.cookitup.domain.model.MeasureUnit
import com.example.cookitup.domain.model.Measures
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.model.Step

object MapperDto {
    fun mapToDomain(dto: RecipeDto): Recipe {
        return Recipe(
            id = dto.id.toString(),
            image = dto.imageUrl.orEmpty(),
            title = dto.title.orEmpty()
        )
    }

    fun mapToDomain(dto: RecipeDetailDto, instructions: RecipeInstructions): RecipeDetail {
        return RecipeDetail(
            title = dto.title ?: "",
            image = dto.imageUrl.orEmpty(),
            time = dto.readyInMinutes.toString(),
            servings = dto.servings ?: 0,
            types = dto.dishTypes.orEmpty(),
            ingredients = dto.extendedIngredients.orEmpty().map { mapToDomain(it) },
            id = dto.id.toString(),
            instructions = instructions
        )
    }

    fun mapToDomain(dto: RecipeInstructionsDto): RecipeInstructions {
        return RecipeInstructions(
            steps = dto.steps.orEmpty().map { Step(it.num.toString(), it.step.orEmpty()) }
        )
    }

    private fun mapToDomain(dto: IngredientDto): Ingredient {
        return Ingredient(
            id = dto.id.toString(),
            image = dto.imageUrl ?: "",
            name = dto.name.orEmpty(),
            measures = mapToDomain(dto.measures)
        )
    }

    private fun mapToDomain(dto: MeasureUnitDto): MeasureUnit {
        if (dto.amount != null) {
            val roundedAmount = when {
                dto.amount >= 1 -> dto.amount.toInt()
                else -> "%.1f".format(dto.amount)
            }
            return MeasureUnit(
                amount = roundedAmount.toString(),
                unit = dto.unitShort.orEmpty()
            )
        } else {
            return MeasureUnit("", "")
        }
    }

    private fun mapToDomain(dto: MeasuresDto): Measures {
        return Measures(
            metric = mapToDomain(dto.metric)
        )
    }
}
