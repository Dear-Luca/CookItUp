package com.example.cookitup.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeInstructionsDto(
    @SerializedName("steps") val steps: List<StepDto>
)

data class StepDto(
    @SerializedName("number") val num: Int,
    @SerializedName("step") val step: String
)
