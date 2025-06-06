package com.example.cookitup.ui.screens.recipes

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.cookitup.R
import com.example.cookitup.ui.navigation.Routes
import com.example.cookitup.ui.screens.components.BottomBar
import com.example.cookitup.ui.screens.components.TopBar
import com.example.cookitup.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recipes(
    ingredients: List<String>?,
    similarRecipesId: String?,
    state: RecipesState,
    actions: RecipesActions,
    navController: NavHostController
) {
    if (ingredients != null) {
        LaunchedEffect(ingredients) {
            if (state is RecipesState.Loading) {
                actions.fetchRecipes(ingredients)
            }
        }
    }

    if (similarRecipesId != null) {
        LaunchedEffect(similarRecipesId) {
            if (state is RecipesState.Loading) {
                actions.fetchSimilarRecipes(similarRecipesId)
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = { TopBar(navController, stringResource(R.string.title_recipes), scrollBehavior) },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (state) {
                is RecipesState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                is RecipesState.Success -> {
                    // remember the callback
                    val onRecipeClick = remember(navController, snackbarHostState, context, scope) {
                        onClick(
                            navController,
                            scope,
                            snackbarHostState,
                            context
                        )
                    }
                    RecipeItems(
                        state.recipes,
                        onRecipeClick
                    )
                }
                is RecipesState.Error -> Text(
                    text = state.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

fun onClick(
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    context: Context
): (String) -> Unit = { recipeId ->
    scope.launch {
        NetworkUtils.checkConnectivity(context, snackbarHostState) {
            navController.navigate(Routes.RecipeDetail(recipeId))
        }
    }
}
