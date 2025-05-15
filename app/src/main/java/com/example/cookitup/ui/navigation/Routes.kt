package com.example.cookitup.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
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

sealed class TopLevelRoute<T : Routes>(val name: String, val route: T, val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    data object Home : TopLevelRoute<Routes.SearchRecipes>(
        name = "Home",
        route = Routes.SearchRecipes,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    data object Profile : TopLevelRoute<Routes.Profile>(
        name = "Profile",
        route = Routes.Profile,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle
    )
    data object Favourites : TopLevelRoute<Routes.Favourites>(
        name = "Favourites",
        route = Routes.Favourites,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    )
}
