package com.example.cookitup.ui.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.cookitup.domain.model.Recipe

@Composable
fun RecipeItems(
    recipes: List<Recipe>,
    onClick: (String) -> Unit
) {
    if (recipes.isEmpty()) {
        NoRecipesFound()
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = recipes.size,
                key = { index -> recipes[index].id }
            ) { index ->
                val recipe = recipes[index]
                RecipeItem(
                    recipe,
                    onClick
                )
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    onClick: (id: String) -> Unit
) {
    ElevatedCard(
        onClick = { onClick(recipe.id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image section
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.image)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = "Image for ${recipe.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1.3f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "View recipe",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(18.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun NoRecipesFound() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = "No recipes found",
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Text(
            text = "No recipes found",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Try different ingredients or check your spelling",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}
