package com.example.cookitup.ui.screens.recipeDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

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
        }
    }

    Scaffold { paddingValues ->
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
fun RecipeInfo(state: RecipeDetailState.Success) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = state.detail.image,
                contentDescription = "Image for ${state.detail.title}",
                modifier = Modifier.fillMaxWidth()
            )
            Text(state.detail.title, style = MaterialTheme.typography.titleLarge)
            Text("Time to cook: ${state.detail.time}", style = MaterialTheme.typography.bodyMedium)
            Text("Servings: ${state.detail.servings}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
