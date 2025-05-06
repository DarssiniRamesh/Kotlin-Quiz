package com.example.kotlinquiz.data.local

import androidx.room.*
import com.example.kotlinquiz.data.model.QuizAttemptEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object (DAO) for quiz attempt-related database operations
 */
@Dao
interface QuizAttemptDao {
    /**
     * Get all attempts for a specific quiz
     * @param quizId The unique identifier of the quiz
     * @return A Flow of list of quiz attempts
     */
    @Query("SELECT * FROM quiz_attempts WHERE quizId = :quizId ORDER BY startTime DESC")
    fun getAttemptsForQuiz(quizId: Int): Flow<List<QuizAttemptEntity>>
    
    /**
     * Get all completed quiz attempts
     * @return A Flow of list of completed quiz attempts
     */
    @Query("SELECT * FROM quiz_attempts WHERE isCompleted = 1 ORDER BY endTime DESC")
    fun getCompletedAttempts(): Flow<List<QuizAttemptEntity>>
    
    /**
     * Get a specific quiz attempt by its ID
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The quiz attempt with the specified ID, or null if not found
     */
    @Query("SELECT * FROM quiz_attempts WHERE id = :quizAttemptId")
    suspend fun getQuizAttemptById(quizAttemptId: Int): QuizAttemptEntity?
    
    /**
     * Get the latest attempt for a quiz
     * @param quizId The unique identifier of the quiz
     * @return The most recent quiz attempt for the specified quiz, or null if no attempts exist
     */
    @Query("SELECT * FROM quiz_attempts WHERE quizId = :quizId ORDER BY startTime DESC LIMIT 1")
    suspend fun getLatestAttemptForQuiz(quizId: Int): QuizAttemptEntity?
    
    /**
     * Insert a new quiz attempt
     * @param quizAttempt The quiz attempt to be inserted
     * @return The row ID of the newly inserted quiz attempt
     */
    @Insert
    suspend fun insertQuizAttempt(quizAttempt: QuizAttemptEntity): Long
    
    /**
     * Update a quiz attempt (e.g., when completing a quiz)
     * @param quizAttempt The quiz attempt to be updated
     */
    @Update
    suspend fun updateQuizAttempt(quizAttempt: QuizAttemptEntity)
    
    /**
     * Delete a quiz attempt and its associated answers
     * @param quizAttempt The quiz attempt to be deleted
     */
    @Delete
    suspend fun deleteQuizAttempt(quizAttempt: QuizAttemptEntity)
    
    /**
     * Get quiz performance statistics (average score, attempts) grouped by quiz
     * @return A Flow of pairs of quizId and performance stats
     */
    @Query("SELECT quizId, AVG(score * 100.0 / totalQuestions) as averagePercentage, COUNT(*) as attemptCount " +
           "FROM quiz_attempts WHERE isCompleted = 1 GROUP BY quizId")
    fun getQuizPerformanceStats(): Flow<Map<Int, Pair<Double, Int>>>
    
    /**
     * Get attempts made between two dates
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of quiz attempts within the date range
     */
    @Query("SELECT * FROM quiz_attempts WHERE startTime BETWEEN :startDate AND :endDate ORDER BY startTime DESC")
    suspend fun getAttemptsInDateRange(startDate: Date, endDate: Date): List<QuizAttemptEntity>
}
