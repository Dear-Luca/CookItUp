package com.example.cookitup.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cookitup.domain.model.RecipeDetail
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetail
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailViewModel
import com.example.cookitup.ui.screens.recipes.Recipes
import com.example.cookitup.ui.screens.recipes.RecipesViewModel
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipes
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipesViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

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
    data object StartPage : Routes
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = Routes.SearchRecipes
    ) {
        /*
        Creating the navigation graph
         */

        composable<Routes.SearchRecipes> {
            val searchRecipesViewModel: SearchRecipesViewModel = koinViewModel()
            val searchRecipesState by searchRecipesViewModel.state.collectAsStateWithLifecycle()
            SearchRecipes(
                searchRecipesState,
                searchRecipesViewModel.actions,
                navController
            )
        }
        composable<Routes.Recipes> { navBackStackEntry ->
            val recipesViewModel: RecipesViewModel = koinViewModel()
            val recipesState by recipesViewModel.state.collectAsStateWithLifecycle()
            val route: Routes.Recipes = navBackStackEntry.toRoute()
            Recipes(
                route.ingredients,
                recipesState,
                recipesViewModel.actions,
                navController
            )
        }
        composable<Routes.RecipeDetail> { navBackStackEntry ->
            val recipeDetailViewModel: RecipeDetailViewModel = koinViewModel()
            val recipeDetailState by recipeDetailViewModel.state.collectAsStateWithLifecycle()
            val route: Routes.RecipeDetail = navBackStackEntry.toRoute()
            RecipeDetail(
                route.id,
                recipeDetailState,
                recipeDetailViewModel.actions,
                navController
            )
        }
    }
}
