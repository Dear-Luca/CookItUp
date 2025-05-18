package com.example.cookitup

import androidx.room.Room
import com.example.cookitup.data.local.db.AppDatabase
import com.example.cookitup.data.remote.api.ApiClient
import com.example.cookitup.data.remote.api.SpoonacularApi
import com.example.cookitup.data.repository.ApiRepositoryImpl
import com.example.cookitup.data.repository.DbRepositoryImpl
import com.example.cookitup.domain.repository.ApiRepository
import com.example.cookitup.domain.repository.DbRepository
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailViewModel
import com.example.cookitup.ui.screens.recipes.RecipesViewModel
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single<SpoonacularApi> { ApiClient.retrofitService }

    single { get<AppDatabase>().recipeEntityDao() }
    single { get<AppDatabase>().recipeFullEntityDao() }
    single { get<AppDatabase>().ingredientEntityDao() }
    single { get<AppDatabase>().instructionEntityDao() }
    single { get<AppDatabase>().recipeIngredientCrossRefDao() }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    single<ApiRepository> {
        ApiRepositoryImpl(apiService = get())
    }

    single<DbRepository> {
        DbRepositoryImpl(recipeDAO = get())
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
