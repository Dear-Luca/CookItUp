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
    data class Recipes(val ingredients: List<String>?, val similarRecipesId: String?) : Routes

    @Serializable
    data class RecipeDetail(val id: String) : Routes

    @Serializable
    data object Auth : Routes

    @Serializable
    data object Profile : Routes

    @Serializable
    data object Favourites : Routes

    @Serializable
    data object Settings : Routes

    @Serializable
    data class CookRecipe(val id: String) : Routes
}

sealed class TopLevelRoute(
    val name: String,
    val route: Routes,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Home : TopLevelRoute(
        name = "Home",
        route = Routes.SearchRecipes,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    data object Profile : TopLevelRoute(
        name = "Profile",
        route = Routes.Profile,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle
    )
    data object Favourites : TopLevelRoute(
        name = "Favourites",
        route = Routes.Favourites,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    )
}
