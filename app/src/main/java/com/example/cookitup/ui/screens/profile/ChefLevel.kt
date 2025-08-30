package com.example.cookitup.ui.screens.profile

import androidx.compose.ui.graphics.Color

// Gamification data classes and functions
data class ChefLevel(
    val title: String,
    val description: String,
    val minPosts: Int,
    val maxPosts: Int?,
    val color: Color
)

fun getChefLevel(postCount: Int): ChefLevel {
    return when (postCount) {
        0 -> ChefLevel(
            title = "Dishwasher",
            description = "Just getting started!",
            minPosts = 0,
            maxPosts = 0,
            color = Color(0xFF9E9E9E)
        )
        in 1..9 -> ChefLevel(
            title = "Commis",
            description = "Learning the basics",
            minPosts = 1,
            maxPosts = 9,
            color = Color(0xFF8BC34A)
        )
        in 10..49 -> ChefLevel(
            title = "Sous Chef",
            description = "Building skills",
            minPosts = 10,
            maxPosts = 49,
            color = Color(0xFF2196F3)
        )
        in 50..99 -> ChefLevel(
            title = "Chef",
            description = "Mastering the craft",
            minPosts = 50,
            maxPosts = 99,
            color = Color(0xFFFF9800)
        )
        else -> ChefLevel(
            title = "Executive Chef",
            description = "Culinary master!",
            minPosts = 100,
            maxPosts = null,
            color = Color(0xFFFFD700)
        )
    }
}
