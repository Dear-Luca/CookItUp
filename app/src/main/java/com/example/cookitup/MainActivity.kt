package com.example.cookitup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookitup.ui.theme.CookItUpTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookItUpTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    var query by remember { mutableStateOf("") }
                    var recipes by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        BasicTextField(
                            value = query,
                            onValueChange = { query = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                search(
                                    food = query,
                                    function = {
                                        recipes = it
                                    }

                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Search Recipes")
                        }
                        Spacer(modifier = Modifier.padding(16.dp))
                        Text(recipes)
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
}
