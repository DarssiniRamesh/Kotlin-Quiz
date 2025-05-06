package com.example.kotlinquiz.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Data class representing a Category entity
 * Categories are used to organize quizzes by topic or subject
 */
@Entity(
    tableName = "categories",
    indices = [Index("name", unique = true)]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val iconUrl: String? = null
)
