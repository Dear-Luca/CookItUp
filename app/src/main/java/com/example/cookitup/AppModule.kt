package com.example.cookitup

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.cookitup.data.local.db.AppDatabase
import com.example.cookitup.data.remote.api.ApiClient
import com.example.cookitup.data.remote.api.SpoonacularApi
import com.example.cookitup.data.repository.ApiRepositoryImpl
import com.example.cookitup.data.repository.CacheRepositoryImpl
import com.example.cookitup.data.repository.DataStoreRepositoryImpl
import com.example.cookitup.data.repository.DbRepositoryImpl
import com.example.cookitup.data.repository.SupabaseRepositoryImpl
import com.example.cookitup.domain.repository.ApiRepository
import com.example.cookitup.domain.repository.CacheRepository
import com.example.cookitup.domain.repository.DbRepository
import com.example.cookitup.domain.repository.SupabaseRepository
import com.example.cookitup.ui.screens.auth.AuthViewModel
import com.example.cookitup.ui.screens.cookRecipe.CookRecipeViewModel
import com.example.cookitup.ui.screens.favourites.FavouritesViewModel
import com.example.cookitup.ui.screens.profile.ProfileViewModel
import com.example.cookitup.ui.screens.recipeDetail.RecipeDetailViewModel
import com.example.cookitup.ui.screens.recipes.RecipesViewModel
import com.example.cookitup.ui.screens.searchRecipes.SearchRecipesViewModel
import com.example.cookitup.ui.screens.settings.ThemeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore
    by preferencesDataStore("theme")

val koinModule = module {
    single<SpoonacularApi> { ApiClient.retrofitService }

    single { get<AppDatabase>().recipeEntityDao() }
    single { get<AppDatabase>().recipeFullEntityDao() }
    single { get<AppDatabase>().ingredientEntityDao() }
    single { get<AppDatabase>().instructionEntityDao() }
    single { get<AppDatabase>().recipeIngredientCrossRefDao() }

    single { get<Context>().dataStore }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration(false).build()
    }

    single<ApiRepository> {
        ApiRepositoryImpl(apiService = get())
    }

    single<CacheRepository> {
        CacheRepositoryImpl()
    }

    single<SupabaseRepository> {
        SupabaseRepositoryImpl()
    }

    single<DbRepository> {
        DbRepositoryImpl(
            recipeDAO = get(),
            instructionDao = get(),
            crossRefDao = get(),
            recipeFullDao = get(),
            ingredientDao = get()
        )
    }

    single { DataStoreRepositoryImpl(get()) }

    viewModel {
        RecipesViewModel(repository = get())
    }

    viewModel {
        SearchRecipesViewModel()
    }

    viewModel {
        RecipeDetailViewModel(
            apiRepository = get(),
            dbRepository = get(),
            cacheRepository = get()
        )
    }

    viewModel {
        FavouritesViewModel(get())
    }

    viewModel {
        AuthViewModel(get())
    }

    viewModel {
        ProfileViewModel(get())
    }

    viewModel {
        ThemeViewModel(get())
    }

    viewModel {
        CookRecipeViewModel(
            supabaseRepository = get(),
            cacheRepository = get()
        )
    }
}
