package com.example.cookitup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.cookitup.ui.navigation.NavGraph
import com.example.cookitup.ui.screens.settings.Theme
import com.example.cookitup.ui.screens.settings.ThemeViewModel
import com.example.cookitup.ui.theme.CookItUpTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: ThemeViewModel = getViewModel()
            val state by settingsViewModel.state.collectAsStateWithLifecycle()
            CookItUpTheme(
                darkTheme = when (state.theme) {
                    Theme.Light -> false
                    Theme.Dark -> true
                    Theme.System -> isSystemInDarkTheme()
                }
            ) {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
