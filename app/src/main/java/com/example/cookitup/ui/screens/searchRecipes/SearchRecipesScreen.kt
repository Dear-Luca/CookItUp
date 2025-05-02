package com.example.cookitup.ui.screens.searchRecipes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cookitup.ui.Routes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchRecipes(
    state: SearchRecipesState,
    actions: SearchRecipesActions,
    navController: NavHostController
) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            var text by remember { mutableStateOf("") }
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Enter an ingredient", style = MaterialTheme.typography.bodyMedium) }
                )
                OutlinedButton(
                    onClick = {
                        actions.addIngredient(text)
                        text = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Add",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))

            FlowRow(
                modifier = Modifier.padding(8.dp)
            ) {
                state.ingredients.forEach { ingredient ->
                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 4.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Text(
                            text = ingredient,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = { navController.navigate(Routes.Recipes(state.ingredients)) },
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Text(
                    "Search recipes",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
