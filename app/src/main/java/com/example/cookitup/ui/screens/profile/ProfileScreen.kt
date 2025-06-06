package com.example.cookitup.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.cookitup.ui.screens.components.SettingsComponent
import com.example.cookitup.ui.screens.components.TopBar
import com.example.cookitup.ui.screens.components.UserCard
import com.example.cookitup.utils.NetworkUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavHostController,
    state: ProfileState,
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                navController,
                stringResource(R.string.title_profile),
                actions = {
                    SettingsComponent(
                        onSettingsClick = { navController.navigate(Routes.Settings) }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { BottomBar(navController) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            when (state) {
                is ProfileState.Loading ->
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                is ProfileState.Error -> Text(
                    text = state.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                is ProfileState.Success -> UserCard(state.user)
            }
        }
    }
}
