package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface that defines data operations for quiz categories
 */
interface CategoryRepository {
    /**
     * Get all categories
     * @return A Flow of list of categories
     */
    fun getAllCategories(): Flow<List<CategoryEntity>>
    
    /**
     * Get a specific category by its ID
     * @param categoryId The unique identifier for the category
     * @return The category with the specified ID, or null if not found
     */
    suspend fun getCategoryById(categoryId: Int): CategoryEntity?
    
    /**
     * Get a category by name
     * @param name The name of the category
     * @return The category with the specified name, or null if not found
     */
    suspend fun getCategoryByName(name: String): CategoryEntity?
    
    /**
     * Add a new category
     * @param category The category to add
     * @return The ID of the newly created category, or -1 if the operation failed
     */
    suspend fun addCategory(category: CategoryEntity): Long
    
    /**
     * Add multiple categories
     * @param categories The list of categories to add
     * @return The IDs of the newly created categories
     */
    suspend fun addCategories(categories: List<CategoryEntity>): List<Long>
    
    /**
     * Update an existing category
     * @param category The category to update
     */
    suspend fun updateCategory(category: CategoryEntity)
    
    /**
     * Delete a category by its ID
     * @param categoryId The unique identifier for the category to delete
     */
    suspend fun deleteCategory(categoryId: Int)
    
    /**
     * Get the number of quizzes in each category
     * @return A Flow of pairs of categoryId and count
     */
    fun getCategoriesWithQuizCount(): Flow<Map<Int, Int>>
}
