package com.example.cookitup.ui.screens.recipeDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.cookitup.ui.screens.components.TopBar

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

    Scaffold(
        topBar = { TopBar(navController, "Recipe Details") }
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
fun RecipeInfo(state: RecipeDetailState.Success) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = state.detail.image,
                contentDescription = "Image for ${state.detail.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = state.detail.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                LabeledText("Time to cook", "${state.detail.time} min", icon = Icons.Filled.Timer)
                LabeledText("Servings", state.detail.servings.toString(), icon = Icons.Filled.Person)
                LabeledText("Dish types", state.detail.types.joinToString(", "), icon = Icons.Filled.RestaurantMenu)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                state.detail.ingredients.forEach {
                    LabeledText(
                        label = it.name.replaceFirstChar(Char::uppercase),
                        value = "${it.measures.metric.amount} ${it.measures.metric.unit}"
                    )
                }
            }
        }
    }
}

@Composable
fun LabeledText(label: String, value: String, icon: ImageVector? = null) {
    Row {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(18.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = buildAnnotatedString {
                withStyle(style = MaterialTheme.typography.titleSmall.toSpanStyle()) {
                    append("$label: ")
                }
                withStyle(style = MaterialTheme.typography.bodyMedium.toSpanStyle()) {
                    append(value)
                }
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
