package com.example.cookitup.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailActions
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailState

@Composable
fun ToggleFavourite(
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
