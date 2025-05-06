package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.model.AnswerEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface that defines data operations for quiz answers
 */
interface AnswerRepository {
    /**
     * Get all answers for a specific quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return A list of answers associated with the quiz attempt
     */
    suspend fun getAnswersForAttempt(quizAttemptId: Int): List<AnswerEntity>
    
    /**
     * Get a specific answer by question and attempt IDs
     * @param questionId The unique identifier of the question
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The answer for the specified question in the specified attempt, or null if not found
     */
    suspend fun getAnswerForQuestion(questionId: Int, quizAttemptId: Int): AnswerEntity?
    
    /**
     * Save an answer for a question in a quiz attempt
     * @param answer The answer to save
     * @return The ID of the newly created or updated answer
     */
    suspend fun saveAnswer(answer: AnswerEntity): Long
    
    /**
     * Save multiple answers at once
     * @param answers The list of answers to save
     * @return The IDs of the newly created answers
     */
    suspend fun saveAnswers(answers: List<AnswerEntity>): List<Long>
    
    /**
     * Delete all answers for a specific quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     */
    suspend fun deleteAnswersForAttempt(quizAttemptId: Int)
    
    /**
     * Get the count of correct answers for a quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The number of correct answers in the specified attempt
     */
    suspend fun getCorrectAnswerCountForAttempt(quizAttemptId: Int): Int
    
    /**
     * Get the average time spent on questions for a quiz attempt
     * @param quizAttemptId The unique identifier of the quiz attempt
     * @return The average time spent in seconds
     */
    suspend fun getAverageTimeSpentForAttempt(quizAttemptId: Int): Double
    
    /**
     * Get the most difficult questions (most incorrect answers)
     * @return A Flow of map of question IDs and their incorrect answer counts
     */
    fun getMostDifficultQuestions(): Flow<Map<Int, Int>>
}
