package com.example.cookitup.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.cookitup.domain.model.RecipeInstructions

@Entity("recipe")
data class RecipeEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val image: String,
    val time: String,
    val servings: Int,
    val dishTypes: List<String>
)

@Entity(
    "recipeIngredientCrossRef",
    primaryKeys = ["recipeId", "ingredientId"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RecipeIngredientEntity::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeIngredientCrossRef(
    val recipeId: Long,
    val ingredientId: Long
)

data class RecipeFull(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RecipeIngredientCrossRef::class,
            parentColumn = "recipeId",
            entityColumn = "ingredientId"
        )
    )
    val ingredients: List<RecipeIngredientEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val instructions: List<RecipeInstructions>
)
