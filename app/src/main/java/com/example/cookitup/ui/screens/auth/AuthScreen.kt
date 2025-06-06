package com.example.cookitup.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cookitup.ui.navigation.Routes

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
