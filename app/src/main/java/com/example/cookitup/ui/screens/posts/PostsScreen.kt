package com.example.cookitup.ui.screens.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.cookitup.R
import com.example.cookitup.ui.navigation.Routes
import com.example.cookitup.ui.screens.components.BottomBar
import com.example.cookitup.ui.screens.components.TopBar
import com.example.cookitup.ui.screens.settings.SettingsComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Posts(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                stringResource(R.string.title_posts),
                actions = {
                    SettingsComponent(
                        onSettingsClick = {
                            navController.navigate(
                                Routes.Settings
                            )
                        }
                    )
                }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
        }
    }
}
