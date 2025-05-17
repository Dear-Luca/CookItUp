package com.example.cookitup.data.repository

import com.example.cookitup.data.local.dao.RecipeDao
import com.example.cookitup.domain.repository.DbRepository

class DbRepositoryImpl(
    private val recipeDAO: RecipeDao
) : DbRepository
