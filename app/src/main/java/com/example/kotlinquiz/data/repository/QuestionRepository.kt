package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.model.Question
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface that defines the data operations for Quiz Questions
 */
interface QuestionRepository {
    /**
     * Get all questions for a specific quiz
     * @param quizId The unique identifier for the quiz
     * @return A Flow of list of questions for the quiz
     */
    fun getQuestionsForQuiz(quizId: Int): Flow<List<Question>>
    
    /**
     * Get a specific question by its ID
     * @param questionId The unique identifier for the question
     * @return The question with the specified ID, or null if not found
     */
    suspend fun getQuestionById(questionId: Int): Question?
    
    /**
     * Add a new question to a quiz
     * @param question The question to add
     * @return The ID of the newly created question, or -1 if the operation failed
     */
    suspend fun addQuestion(question: Question): Long
    
    /**
     * Add multiple questions to a quiz
     * @param questions The list of questions to add
     * @return The IDs of the newly created questions
     */
    suspend fun addQuestions(questions: List<Question>): List<Long>
    
    /**
     * Update an existing question
     * @param question The question to update
     */
    suspend fun updateQuestion(question: Question)
    
    /**
     * Delete a question by its ID
     * @param questionId The unique identifier for the question to delete
     */
    suspend fun deleteQuestion(questionId: Int)
    
    /**
     * Delete all questions for a specific quiz
     * @param quizId The unique identifier for the quiz
     */
    suspend fun deleteQuestionsForQuiz(quizId: Int)
}
