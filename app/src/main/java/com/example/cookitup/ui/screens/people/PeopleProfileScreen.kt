package com.example.cookitup.ui.screens.people

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.cookitup.ui.screens.profile.PostsState
import com.example.cookitup.ui.screens.profile.ProfileActions

@Composable
fun PeopleProfile(
    id: String,
    image: String?,
    username: String,
    actions: ProfileActions,
    postsState: PostsState,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        actions.getPosts(id)
    }
}
