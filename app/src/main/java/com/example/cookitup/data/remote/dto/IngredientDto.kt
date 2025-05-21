package com.example.cookitup.data.remote.dto

import com.google.gson.annotations.SerializedName

data class IngredientDto(
    @SerializedName("id") val id: Long,
    @SerializedName("image") val imageUrl: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("amount") val amount: Double?,
    @SerializedName("measures") val measures: MeasuresDto
)

data class MeasuresDto(
    @SerializedName("metric") val metric: MeasureUnitDto,
    @SerializedName("us") val us: MeasureUnitDto

)

data class MeasureUnitDto(
    @SerializedName("amount") val amount: Double?,
    @SerializedName("unitLong") val unitLong: String?,
    @SerializedName("unitShort") val unitShort: String?
)
