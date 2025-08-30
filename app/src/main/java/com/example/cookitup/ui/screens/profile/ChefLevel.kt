package com.example.cookitup.ui.screens.profile

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

// Gamification data classes and functions
data class ChefLevel(
    val title: String,
    val description: String,
    val minPosts: Int,
    val maxPosts: Int?,
    val color: Color = Color(0xFF82B1FF)
)

fun getChefLevel(postCount: Int): ChefLevel {
    return when (postCount) {
        0 -> ChefLevel(
            title = "Dishwasher",
            description = "Just getting started!",
            minPosts = 0,
            maxPosts = 0
        )
        in 1..9 -> ChefLevel(
            title = "Commis",
            description = "Learning the basics",
            minPosts = 1,
            maxPosts = 9
        )
        in 10..49 -> ChefLevel(
            title = "Sous Chef",
            description = "Building skills",
            minPosts = 10,
            maxPosts = 49
        )
        in 50..99 -> ChefLevel(
            title = "Chef",
            description = "Mastering the craft",
            minPosts = 50,
            maxPosts = 99
        )
        else -> ChefLevel(
            title = "Executive Chef",
            description = "Culinary master!",
            minPosts = 100,
            maxPosts = null
        )
    }
}
