package com.example.cookitup.ui.screens.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cookitup.network.API_KEY
import com.example.cookitup.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun Recipes(ingredients: List<String>) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            ingredients.forEach {
                Text(it)
            }
        }
    }
}

private fun search(ingredients: List<String>) {
    val ingredientsList = ingredients.joinToString(",")
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val recipes = ApiClient.retrofitService.searchRecipes(ingredientsList, API_KEY)
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }
}
