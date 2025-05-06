package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface that defines the data operations for the Quiz functionality
 */
interface QuizRepository {
    /**
     * Get all quizzes as a Flow
     */
    fun getAllQuizzes(): Flow<List<Quiz>>
    
    /**
     * Get a quiz by its ID
     * @param quizId The unique identifier for the quiz
     */
    suspend fun getQuizById(quizId: Int): Quiz?
    
    /**
     * Insert a new quiz or update an existing quiz
     * @param quiz The quiz to be saved
     * @return The ID of the newly inserted quiz or -1 if update
     */
    suspend fun saveQuiz(quiz: Quiz): Long
    
    /**
     * Delete a quiz by its ID
     * @param quizId The unique identifier for the quiz to be deleted
     */
    suspend fun deleteQuiz(quizId: Int)
    
    /**
     * Mark a quiz as completed
     * @param quizId The unique identifier for the quiz
     * @param completed Whether the quiz is completed or not
     */
    suspend fun markQuizAsCompleted(quizId: Int, completed: Boolean = true)
}
