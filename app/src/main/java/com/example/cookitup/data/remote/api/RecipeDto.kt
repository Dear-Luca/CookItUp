package com.example.cookitup.data.remote.api

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String
)
