package com.example.cookitup.data.repository

import com.example.cookitup.data.local.dao.RecipesDAO
import com.example.cookitup.domain.repository.DbRepository

class DbRepositoryImpl(
    private val recipesDAO: RecipesDAO
) : DbRepository
