package com.example.cookitup.ui.screens.searchRecipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookitup.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchRecipes(
    state : SearchRecipesState,
    actions: SearchRecipesActions
){
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            var text by remember { mutableStateOf("") }
            Row {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Label") },
                )
                Button(
                    onClick = {
                        actions.addIngredient(text)
                        text = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add")
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))
            
            LazyColumn {
                items(state.ingredients) { ingredient ->
                    Text(ingredient)
                }
            }
        }
    }
}

private fun search(food: String, function: (String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = ApiClient.retrofitService.searchRecipes(food)
            val recipes = response.results.joinToString("\n") { it.title }
            withContext(Dispatchers.Main) {
                function(recipes)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
