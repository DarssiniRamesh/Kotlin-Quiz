package com.example.kotlinquiz.data.local

import androidx.room.*
import com.example.kotlinquiz.data.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for category-related database operations
 */
@Dao
interface CategoryDao {
    /**
     * Get all categories from the database
     * @return A Flow of list of categories
     */
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>
    
    /**
     * Get a specific category by its ID
     * @param categoryId The unique identifier of the category
     * @return The category with the specified ID, or null if not found
     */
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): CategoryEntity?
    
    /**
     * Get a category by name
     * @param name The name of the category
     * @return The category with the specified name, or null if not found
     */
    @Query("SELECT * FROM categories WHERE name = :name LIMIT 1")
    suspend fun getCategoryByName(name: String): CategoryEntity?
    
    /**
     * Insert a new category or update an existing one if it has the same ID
     * @param category The category to be inserted or updated
     * @return The row ID of the newly inserted category, or -1 if the insert failed
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long
    
    /**
     * Insert multiple categories at once
     * @param categories The list of categories to be inserted
     * @return The row IDs of the newly inserted categories
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>): List<Long>
    
    /**
     * Update an existing category
     * @param category The category to be updated
     */
    @Update
    suspend fun updateCategory(category: CategoryEntity)
    
    /**
     * Delete a category
     * @param category The category to be deleted
     */
    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
    
    /**
     * Get the number of quizzes in each category
     * @return A Flow of pairs of categoryId and count
     */
    @Query("SELECT c.id, COUNT(q.id) as quizCount FROM categories c LEFT JOIN quizzes q ON c.id = q.categoryId GROUP BY c.id")
    fun getCategoriesWithQuizCount(): Flow<Map<Int, Int>>
}
