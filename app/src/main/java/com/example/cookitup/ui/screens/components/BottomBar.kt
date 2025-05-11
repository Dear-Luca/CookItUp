package com.example.cookitup.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cookitup.ui.Routes

@Composable
fun BottomBar(navController: NavHostController) {
    BottomAppBar(
        actions = {
            IconButton(
                onClick = { navController.navigate(Routes.SearchRecipes) }
            ) {
                Icon(Icons.Default.Home, "home page")
            }
            IconButton(
                onClick = { navController.navigate(Routes.Favourites) }
            ) {
                Icon(Icons.Default.Favorite, "favourites page")
            }
            IconButton(
                onClick = { navController.navigate(Routes.Profile) }
            ) {
                Icon(Icons.Default.AccountCircle, "profile page")
            }
        }
    )
}
