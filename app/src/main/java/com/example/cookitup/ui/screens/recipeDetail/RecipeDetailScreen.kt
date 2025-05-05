package com.example.cookitup.ui.screens.recipeDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun RecipeDetail(
    id: String,
    state: RecipeDetailState,
    actions: RecipeDetailActions,
    navController: NavHostController
) {
    LaunchedEffect(id) {
        actions.fetchRecipeDetail(id)
    }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (state) {
                RecipeDetailState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                is RecipeDetailState.Success -> TODO()
                is RecipeDetailState.Error -> TODO()
            }
        }
    }
}
