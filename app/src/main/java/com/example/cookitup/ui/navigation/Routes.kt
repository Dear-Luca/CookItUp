package com.example.cookitup.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object SearchRecipes : Routes

    @Serializable
    data class Recipes(val ingredients: List<String>) : Routes

    @Serializable
    data class RecipeDetail(val id: String) : Routes

    @Serializable
    data object Login : Routes

    @Serializable
    data object Register : Routes

    @Serializable
    data object Profile : Routes

    @Serializable
    data object Favourites : Routes
}

sealed class TopLevelRoute<T : Routes>(val name: String, val route: T, val icon: ImageVector) {
    data object Home : TopLevelRoute<Routes.SearchRecipes>(
        name = "Home",
        route = Routes.SearchRecipes,
        icon = Icons.Filled.Home
    )
    data object Profile : TopLevelRoute<Routes.Profile>(
        name = "Profile",
        route = Routes.Profile,
        icon = Icons.Filled.AccountCircle
    )
    data object Favourites : TopLevelRoute<Routes.Favourites>(
        name = "Favourites",
        route = Routes.Favourites,
        icon = Icons.Filled.Favorite
    )
}
