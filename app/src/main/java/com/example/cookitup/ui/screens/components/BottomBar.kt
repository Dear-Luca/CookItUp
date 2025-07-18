package com.example.cookitup.ui.screens.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cookitup.ui.navigation.Routes
import com.example.cookitup.ui.navigation.TopLevelRoute

@Composable
fun BottomBar(navController: NavHostController) {
    val topLevelRoutes = listOf(
        TopLevelRoute.Home,
        TopLevelRoute.Favourites,
        TopLevelRoute.Profile
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        topLevelRoutes.forEach { topLevelRoute ->
            val isSelected = currentDestination
                ?.hierarchy
                ?.any { it.route == topLevelRoute.route::class.qualifiedName } == true
            NavigationBarItem(
                selected = isSelected,
                icon = {
                    Icon(
                        imageVector = if (isSelected) topLevelRoute.selectedIcon else topLevelRoute.unselectedIcon,
                        contentDescription = topLevelRoute.name,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = topLevelRoute.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.7f
                            )
                        }
                    )
                },
                onClick = {
                    when (topLevelRoute) {
                        is TopLevelRoute.Home -> {
                            val popped = navController.popBackStack(
                                route = Routes.SearchRecipes,
                                inclusive = false
                            )
                            if (!popped) {
                                navController.navigate(Routes.SearchRecipes)
                            }
                        }
                        is TopLevelRoute.Favourites -> {
                            val popped = navController.popBackStack(
                                route = Routes.Favourites,
                                inclusive = false
                            )
                            if (!popped) {
                                navController.navigate(Routes.Favourites)
                            }
                        }
                        is TopLevelRoute.Profile -> {
                            val popped = navController.popBackStack(
                                route = Routes.Profile,
                                inclusive = false
                            )
                            if (!popped) {
                                navController.navigate(Routes.Profile)
                            }
                        }
//                        else -> {
//                            navController.navigate(topLevelRoute.route) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//                                launchSingleTop = true
//                                restoreState = true
//                            }
//                        }
                    }
                }
            )
        }
    }
}
