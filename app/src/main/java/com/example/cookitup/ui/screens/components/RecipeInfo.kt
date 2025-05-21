package com.example.cookitup.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailState

@Composable
fun RecipeInfo(state: RecipeDetailState.Success) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(rememberScrollState(), true),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.detail.image)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = "Image for ${state.detail.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
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

            HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = "Ingredients:",
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

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Instructions:",
                style = MaterialTheme.typography.titleMedium
            )

            state.detail.instructions.steps.forEach { step ->
                LabeledText(step.num, step.instruction)
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
            }
        )
    }
}
