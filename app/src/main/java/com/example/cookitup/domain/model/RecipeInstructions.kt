package com.example.cookitup.domain.model

data class RecipeInstructions(
    val steps: List<Step>
)

data class Step(
    val num: String,
    val step: String
)
