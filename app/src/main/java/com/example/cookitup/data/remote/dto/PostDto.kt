package com.example.cookitup.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String,
    val image: String,
    val recipe: String,
    @SerialName("user_id") val userId: String
)
