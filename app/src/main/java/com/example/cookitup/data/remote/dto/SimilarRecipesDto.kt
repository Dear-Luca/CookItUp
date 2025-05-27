package com.example.cookitup.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SimilarRecipesDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("image") val image: String?
)
