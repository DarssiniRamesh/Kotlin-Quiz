package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.local.CategoryDao
import com.example.kotlinquiz.data.model.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of CategoryRepository that uses Room database for local storage
 * @param categoryDao The DAO for category-related database operations
 */
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    
    override fun getAllCategories(): Flow<List<CategoryEntity>> {
package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.local.CategoryDao
import com.example.kotlinquiz.data.model.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of CategoryRepository that uses Room database for local storage
 * @param categoryDao The DAO for category-related database operations
 */
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    
    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }
    
    override suspend fun getCategoryById(categoryId: Int): CategoryEntity? {
        return categoryDao.getCategoryById(categoryId)
    }
    
    override suspend fun getCategoryByName(name: String): CategoryEntity? {
        return categoryDao.getCategoryByName(name)
    }
    
    override suspend fun addCategory(category: CategoryEntity): Long {
        return categoryDao.insertCategory(category)
    }
    
    override suspend fun addCategories(categories: List<CategoryEntity>): List<Long> {
        return categoryDao.insertCategories(categories)
    }
    
    override suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.updateCategory(category)
    }
    
    override suspend fun deleteCategory(categoryId: Int) {
        categoryDao.getCategoryById(categoryId)?.let { category ->
            categoryDao.deleteCategory(category)
        }
    }
    
    override fun getCategoriesWithQuizCount(): Flow<Map<Int, Int>> {
        return categoryDao.getCategoriesWithQuizCount()
    }
}
