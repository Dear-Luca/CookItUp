package com.example.cookitup.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.cookitup.R
import com.example.cookitup.ui.navigation.Routes
import com.example.cookitup.ui.screens.components.BottomBar
import com.example.cookitup.ui.screens.components.TopBar
import com.example.cookitup.ui.screens.settings.SettingsComponent
import com.example.cookitup.utils.NetworkUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavHostController,
    profileState: ProfileState,
    actions: ProfileActions
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(Unit) {
        NetworkUtils.checkConnectivity(
            context,
            snackbarHostState
        ) {
            actions.getCurrentUser()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                navController,
                stringResource(R.string.title_profile),
                actions = {
                    SettingsComponent(
                        onSettingsClick = {
                            navController.navigate(
                                Routes.Settings
                            )
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (profileState) {
                is ProfileState.Loading ->
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                is ProfileState.Error -> Text(
                    text = profileState.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                is ProfileState.Success -> UserCard(
                    profileState.user,
                    actions,
                    onNavigateToUserPosts = { navController.navigate(Routes.Posts) },
                    onNavigateToSettings = { navController.navigate(Routes.Settings) },
                    onNavigateToFavourites = { navController.navigate(Routes.Favourites) }
                )
            }
        }
    }
}
