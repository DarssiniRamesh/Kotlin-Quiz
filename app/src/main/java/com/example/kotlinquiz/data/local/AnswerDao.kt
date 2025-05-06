package com.example.kotlinquiz.data.local

import androidx.room.*
import com.example.kotlinquiz.data.model.AnswerEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for answer-related database operations
 */
@Dao
interface AnswerDao {
    /**
     * Get all answers for a specific quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return A list of answers associated with the quiz attempt
     */
    @Query("SELECT * FROM answers WHERE quizAttemptId = :quizAttemptId")
    suspend fun getAnswersForAttempt(quizAttemptId: Int): List<AnswerEntity>
    
    /**
     * Get a specific answer by question and attempt IDs
     * @param questionId The unique identifier of the question
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The answer for the specified question in the specified attempt, or null if not found
     */
    @Query("SELECT * FROM answers WHERE questionId = :questionId AND quizAttemptId = :quizAttemptId LIMIT 1")
    suspend fun getAnswerForQuestion(questionId: Int, quizAttemptId: Int): AnswerEntity?
    
    /**
     * Insert a new answer
     * @param answer The answer to be inserted
     * @return The row ID of the newly inserted answer
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: AnswerEntity): Long
    
    /**
     * Insert multiple answers at once
     * @param answers The list of answers to be inserted
     * @return The row IDs of the newly inserted answers
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<AnswerEntity>): List<Long>
    
    /**
     * Update an existing answer
     * @param answer The answer to be updated
     */
    @Update
    suspend fun updateAnswer(answer: AnswerEntity)
    
    /**
     * Delete an answer
     * @param answer The answer to be deleted
     */
    @Delete
    suspend fun deleteAnswer(answer: AnswerEntity)
    
    /**
     * Delete all answers for a specific quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     */
    @Query("DELETE FROM answers WHERE quizAttemptId = :quizAttemptId")
    suspend fun deleteAnswersForAttempt(quizAttemptId: Int)
    
    /**
     * Get the count of correct answers for a quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The number of correct answers in the specified attempt
     */
    @Query("SELECT COUNT(*) FROM answers WHERE quizAttemptId = :quizAttemptId AND isCorrect = 1")
    suspend fun getCorrectAnswerCountForAttempt(quizAttemptId: Int): Int
    
    /**
     * Get the average time spent on questions for a quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The average time spent in seconds
     */
    @Query("SELECT AVG(timeSpentInSeconds) FROM answers WHERE quizAttemptId = :quizAttemptId")
    suspend fun getAverageTimeSpentForAttempt(quizAttemptId: Int): Double
    
    /**
     * Get the most difficult questions (most incorrect answers)
     * @return A list of question IDs and their incorrect answer counts, sorted by count descending
     */
    @Query("SELECT questionId, COUNT(*) as incorrectCount FROM answers WHERE isCorrect = 0 GROUP BY questionId ORDER BY incorrectCount DESC LIMIT 10")
    fun getMostDifficultQuestions(): Flow<Map<Int, Int>>
}
