package com.example.cookitup.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cookitup.model.Recipe
import com.example.cookitup.model.RecipeResponse
import com.example.cookitup.network.ApiClient
import com.example.cookitup.ui.screens.recipes.Recipes
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipes
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

sealed interface Routes{
    @Serializable
    data object SearchRecipes : Routes

    @Serializable
    data class Recipes(val recipes: List<Recipe>) : Routes

    @Serializable
    data object RecipeDetail: Routes

    @Serializable
    data object Login : Routes

    @Serializable
    data object Register : Routes

    @Serializable
    data object StartPage: Routes

}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController,
        startDestination = Routes.SearchRecipes
    ){
        /*
        Creating the navigation graph
         */


        composable<Routes.SearchRecipes> {
            val searchRecipesViewModel : SearchRecipesViewModel = viewModel()
            val searchRecipesState by searchRecipesViewModel.state.collectAsStateWithLifecycle()
            SearchRecipes(searchRecipesState, searchRecipesViewModel.actions)
        }
//        composable<Routes.Recipes> { Recipes() }
    }


}


