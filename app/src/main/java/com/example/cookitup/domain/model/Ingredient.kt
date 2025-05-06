package com.example.cookitup.domain.model

data class Ingredient(
    val id: String,
    val image: String,
    val name: String,
    val measures: Measures
)

data class Measures(
    val metric: MeasureUnit
)

data class MeasureUnit(
    val amount: String,
    val unit: String
)
