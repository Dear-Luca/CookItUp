package com.example.cookitup.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.cookitup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, title: String, scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Previous screen")
                }
            }
        },
        scrollBehavior = scrollBehavior,
        actions = {
            if (title == stringResource(R.string.title_recipe_details)) {
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.FavoriteBorder, "Add to favourites")
                }
            }
        }
    )
}
