package com.example.kotlinquiz.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Data class representing a Quiz Attempt entity
 * Tracks user attempts at quizzes, including start/end time and score
 */
@Entity(
    tableName = "quiz_attempts",
    foreignKeys = [
        ForeignKey(
            entity = Quiz::class,
            parentColumns = ["id"],
            childColumns = ["quizId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("quizId")]
)
data class QuizAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quizId: Int,
    val startTime: Date,
    val endTime: Date? = null,
    val score: Int = 0,
    val totalQuestions: Int,
    val isCompleted: Boolean = false
)
