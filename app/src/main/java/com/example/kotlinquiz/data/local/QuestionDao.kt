package com.example.kotlinquiz.data.local

import androidx.room.*
import com.example.kotlinquiz.data.model.Question
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for question-related database operations
 */
@Dao
interface QuestionDao {
    /**
     * Get all questions for a specific quiz
     * @param quizId The unique identifier of the quiz
     * @return A Flow of list of questions
     */
    @Query("SELECT * FROM questions WHERE quizId = :quizId")
    fun getQuestionsForQuiz(quizId: Int): Flow<List<Question>>
    
    /**
     * Get a specific question by its ID
     * @param questionId The unique identifier of the question
     * @return The question with the specified ID, or null if not found
     */
    @Query("SELECT * FROM questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Int): Question?
    
    /**
     * Insert a new question or update an existing one if it has the same ID
     * @param question The question to be inserted or updated
     * @return The row ID of the newly inserted question, or -1 if the insert failed
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question): Long
    
    /**
     * Insert multiple questions at once
     * @param questions The list of questions to be inserted
     * @return The row IDs of the newly inserted questions
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>): List<Long>
    
    /**
     * Update an existing question
     * @param question The question to be updated
     */
    @Update
    suspend fun updateQuestion(question: Question)
    
    /**
     * Delete a question
     * @param question The question to be deleted
     */
    @Delete
    suspend fun deleteQuestion(question: Question)
    
    /**
     * Delete all questions for a specific quiz
     * @param quizId The unique identifier of the quiz
     */
    @Query("DELETE FROM questions WHERE quizId = :quizId")
    suspend fun deleteQuestionsForQuiz(quizId: Int)
}
