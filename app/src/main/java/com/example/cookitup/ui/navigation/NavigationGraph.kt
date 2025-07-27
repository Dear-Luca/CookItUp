package com.example.cookitup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cookitup.ui.screens.auth.Auth
import com.example.cookitup.ui.screens.auth.AuthViewModel
import com.example.cookitup.ui.screens.cookRecipe.CookRecipe
import com.example.cookitup.ui.screens.cookRecipe.CookRecipeViewModel
import com.example.cookitup.ui.screens.favourites.Favourites
import com.example.cookitup.ui.screens.favourites.FavouritesViewModel
import com.example.cookitup.ui.screens.people.People
import com.example.cookitup.ui.screens.posts.Posts
import com.example.cookitup.ui.screens.profile.Profile
import com.example.cookitup.ui.screens.profile.ProfileViewModel
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetail
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailViewModel
import com.example.cookitup.ui.screens.recipes.Recipes
import com.example.cookitup.ui.screens.recipes.RecipesViewModel
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipes
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipesViewModel
import com.example.cookitup.ui.screens.settings.Settings
import com.example.cookitup.ui.screens.settings.ThemeViewModel
import org.koin.androidx.compose.koinViewModel
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = Routes.Auth
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
                route.similarRecipesId,
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

        composable<Routes.CookRecipe> { navBackStackEntry ->
            val cookRecipeViewModel: CookRecipeViewModel = koinViewModel()
            val cookRecipeState by cookRecipeViewModel.state.collectAsStateWithLifecycle()
            val route: Routes.CookRecipe = navBackStackEntry.toRoute()
            val id = route.id

            CookRecipe(
                navController,
                id,
                cookRecipeViewModel.actions,
                cookRecipeState
            )
        }

        composable<Routes.Favourites> {
            val favouritesViewModel: FavouritesViewModel = koinViewModel()
            val favouritesState by favouritesViewModel.state.collectAsStateWithLifecycle()
            Favourites(favouritesState, favouritesViewModel.actions, navController)
        }

        composable<Routes.Profile> {
            val profileViewModel: ProfileViewModel = koinViewModel()
            val profileState by profileViewModel.state.collectAsStateWithLifecycle()
            Profile(navController, profileState, profileViewModel.actions)
        }

        composable<Routes.Auth> {
            val authViewModel: AuthViewModel = koinViewModel()
            val authState by authViewModel.state.collectAsStateWithLifecycle()
            Auth(navController, authState, authViewModel.actions)
        }

        composable<Routes.Settings> {
            val settingsViewModel: ThemeViewModel = koinViewModel()
            val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()

            val profileViewModel: ProfileViewModel = koinViewModel()
            val profileState by profileViewModel.state.collectAsStateWithLifecycle()
            val updatedState by profileViewModel.updateState.collectAsStateWithLifecycle()

            val authViewModel: AuthViewModel = koinViewModel()
            val authState by authViewModel.state.collectAsStateWithLifecycle()

            Settings(
                navController,
                profileState,
                updatedState,
                settingsState,
                authState,
                settingsViewModel.actions,
                profileViewModel.actions,
                authViewModel.actions
            )
        }

        composable<Routes.Posts> {
            Posts(navController)
        }

        composable<Routes.People> {
            People(navController)
        }
    }
}
