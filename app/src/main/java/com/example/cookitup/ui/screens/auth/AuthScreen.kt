package com.example.cookitup.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.cookitup.ui.navigation.Routes
import com.example.cookitup.ui.screens.components.AuthForm

@Composable
fun Auth(
    navController: NavHostController,
    state: AuthState,
    actions: AuthActions
) {
    if (state is AuthState.Authenticated) {
        navController.navigate(Routes.SearchRecipes) {
            popUpTo(Routes.Auth) { inclusive = true }
        }
    } else {
        AuthForm(state, actions)
    }
}
