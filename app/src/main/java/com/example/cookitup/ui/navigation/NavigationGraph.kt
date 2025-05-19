package com.example.cookitup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cookitup.ui.screens.favourites.Favourites
import com.example.cookitup.ui.screens.favourites.FavouritesViewModel
import com.example.cookitup.ui.screens.profile.Profile
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetail
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailViewModel
import com.example.cookitup.ui.screens.recipes.Recipes
import com.example.cookitup.ui.screens.recipes.RecipesViewModel
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipes
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipesViewModel
import org.koin.androidx.compose.koinViewModel

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

        composable<Routes.Favourites> {
            val favouritesViewModel: FavouritesViewModel = koinViewModel()
            val favouritesState by favouritesViewModel.state.collectAsStateWithLifecycle()
            Favourites(favouritesState, favouritesViewModel.actions, navController)
        }

        composable<Routes.Profile> {
            Profile(navController)
        }
    }
}
