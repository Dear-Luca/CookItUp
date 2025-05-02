package com.example.cookitup

import com.example.cookitup.data.remote.api.ApiClient
import com.example.cookitup.data.remote.api.SpoonacularApi
import com.example.cookitup.data.repository.RecipeRepositoryImpl
import com.example.cookitup.domain.repository.RecipeRepository
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailViewModel
import com.example.cookitup.ui.screens.recipes.RecipesViewModel
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single<SpoonacularApi> { ApiClient.retrofitService }

    single<RecipeRepository> {
        RecipeRepositoryImpl(apiService = get())
    }

    viewModel {
        RecipesViewModel(repository = get())
    }

    viewModel {
        SearchRecipesViewModel()
    }

    viewModel {
        RecipeDetailViewModel(get())
    }
}
