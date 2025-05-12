package com.example.cookitup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.cookitup.ui.navigation.NavGraph
import com.example.cookitup.ui.theme.CookItUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookItUpTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
