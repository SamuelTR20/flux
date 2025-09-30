package com.example.flux.repository

import com.example.flux.data.local.CategoryDao
import com.example.flux.data.model.Category

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: Category): Long {
        return categoryDao.insert(category)
    }

    suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAll()
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.delete(category)
    }
}