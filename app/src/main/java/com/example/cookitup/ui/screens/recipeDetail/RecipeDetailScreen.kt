package com.example.cookitup.ui.screens.recipeDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.cookitup.R
import com.example.cookitup.ui.screens.components.BottomBar
import com.example.cookitup.ui.screens.components.RecipeInfo
import com.example.cookitup.ui.screens.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetail(
    id: String,
    state: RecipeDetailState,
    actions: RecipeDetailActions,
    navController: NavHostController
) {
    LaunchedEffect(id) {
        if (state is RecipeDetailState.Loading) {
            actions.fetchRecipeDetail(id)
            actions.observeFavourite(id)
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                navController,
                stringResource(R.string.title_recipe_details),
                scrollBehavior,
                actions = {
                    ToggleFavourite(state, actions)
                }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (state) {
                is RecipeDetailState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                is RecipeDetailState.Success -> RecipeInfo(state)
                is RecipeDetailState.Error -> Text(
                    text = state.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun ToggleFavourite(
    state: RecipeDetailState,
    actions: RecipeDetailActions
) {
    if (state is RecipeDetailState.Success) {
        val isFavourite = state.isFavourite
        IconButton(
            onClick = { actions.toggleFavourites() }
        ) {
            Icon(
                imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavourite) "Remove from favourites" else "Add to favourites"
            )
        }
    }
}
