package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.model.QuizAttemptEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Repository interface that defines data operations for quiz attempts
 */
interface QuizAttemptRepository {
    /**
     * Get all attempts for a specific quiz
     * @param quizId The unique identifier of the quiz
     * @return A Flow of list of quiz attempts
     */
    fun getAttemptsForQuiz(quizId: Int): Flow<List<QuizAttemptEntity>>
    
    /**
     * Get all completed quiz attempts
     * @return A Flow of list of completed quiz attempts
     */
    fun getCompletedAttempts(): Flow<List<QuizAttemptEntity>>
    
    /**
     * Get a specific quiz attempt by its ID
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The quiz attempt with the specified ID, or null if not found
     */
    suspend fun getQuizAttemptById(quizAttemptId: Int): QuizAttemptEntity?
    
    /**
     * Get the latest attempt for a quiz
     * @param quizId The unique identifier of the quiz
     * @return The most recent quiz attempt for the specified quiz, or null if no attempts exist
     */
    suspend fun getLatestAttemptForQuiz(quizId: Int): QuizAttemptEntity?
    
    /**
     * Create a new quiz attempt
     * @param quizAttempt The quiz attempt to create
     * @return The ID of the newly created quiz attempt
     */
    suspend fun createQuizAttempt(quizAttempt: QuizAttemptEntity): Long
    
    /**
     * Update a quiz attempt (e.g., when completing a quiz)
     * @param quizAttempt The quiz attempt to update
     */
    suspend fun updateQuizAttempt(quizAttempt: QuizAttemptEntity)
    
    /**
     * Delete a quiz attempt
     * @param quizAttemptId The ID of the quiz attempt to delete
     */
    suspend fun deleteQuizAttempt(quizAttemptId: Int)
    
    /**
     * Get quiz performance statistics (average score, attempts) grouped by quiz
     * @return A Flow of pairs of quizId and performance stats
     */
    fun getQuizPerformanceStats(): Flow<Map<Int, Pair<Double, Int>>>
    
    /**
     * Get attempts made between two dates
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of quiz attempts within the date range
     */
    suspend fun getAttemptsInDateRange(startDate: Date, endDate: Date): List<QuizAttemptEntity>
}
