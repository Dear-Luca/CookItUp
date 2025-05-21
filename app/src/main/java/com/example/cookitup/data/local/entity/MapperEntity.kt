package com.example.cookitup.data.local.entity

import com.example.cookitup.domain.model.Ingredient
import com.example.cookitup.domain.model.MeasureUnit
import com.example.cookitup.domain.model.Measures
import com.example.cookitup.domain.model.Recipe
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.domain.model.RecipeInstructions
import com.example.cookitup.domain.model.Step

object MapperEntity {
    fun mapToDomain(entity: RecipeEntity): Recipe {
        return Recipe(
            id = entity.id.toString(),
            image = entity.image,
            title = entity.name
        )
    }

    fun mapToDomain(entity: IngredientEntity): Ingredient {
        val measures = MeasureUnit(entity.amount.toString(), entity.unit)
        val metric = Measures(measures)
        return Ingredient(
            entity.id.toString(),
            "",
            entity.name,
            metric

        )
    }

    fun mapToDomain(entities: List<InstructionEntity>): RecipeInstructions {
        val steps = entities.map { instructionEntity ->
            Step(instructionEntity.number.toString(), instructionEntity.instruction)
        }
        return RecipeInstructions(steps)
    }

    fun mapToDomain(entity: RecipeFull): RecipeDetail {
        val types = entity.recipe.dishTypes.split(',')
        return RecipeDetail(
            title = entity.recipe.name,
            image = entity.recipe.image,
            time = entity.recipe.time,
            servings = entity.recipe.servings,
            types = types,
            instructions = mapToDomain(entity.instructions),
            ingredients = entity.ingredients.map { mapToDomain(it) },
            id = entity.recipe.id.toString()
        )
    }

    fun mapToEntity(domain: RecipeDetail): RecipeEntity {
        return RecipeEntity(
            id = domain.id.toLong(),
            name = domain.title,
            image = domain.image,
            time = domain.time,
            servings = domain.servings,
            dishTypes = domain.types.joinToString(",")
        )
    }

    fun mapToEntity(domain: Step, recipeId: String): InstructionEntity {
        return InstructionEntity(
            recipeId.toLong(),
            domain.num.toInt(),
            domain.instruction
        )
    }

    fun mapToEntity(domain: Ingredient): IngredientEntity {
        return IngredientEntity(
            id = domain.id.toLong(),
            name = domain.name,
            amount = domain.measures.metric.amount.toDouble(),
            unit = domain.measures.metric.unit
        )
    }
}
