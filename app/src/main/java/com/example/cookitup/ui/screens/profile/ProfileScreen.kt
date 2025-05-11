package com.example.cookitup.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.cookitup.ui.screens.components.BottomBar
import com.example.cookitup.ui.screens.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = { TopBar(navController, "Profile", scrollBehavior) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) { }
    }
}
