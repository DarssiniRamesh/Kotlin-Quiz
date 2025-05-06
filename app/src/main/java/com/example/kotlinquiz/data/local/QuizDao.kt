package com.example.kotlinquiz.data.local

import androidx.room.*
import com.example.kotlinquiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for quiz-related database operations
 */
@Dao
interface QuizDao {
    /**
     * Get all quizzes from the database
     * @return A Flow of list of quizzes
     */
    @Query("SELECT * FROM quizzes ORDER BY id DESC")
    fun getAllQuizzes(): Flow<List<Quiz>>
    
    /**
     * Get a specific quiz by its ID
     * @param quizId The unique identifier of the quiz
     * @return The quiz with the specified ID, or null if not found
     */
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    suspend fun getQuizById(quizId: Int): Quiz?
    
    /**
     * Insert a new quiz or update an existing one if it has the same ID
     * @param quiz The quiz to be inserted or updated
     * @return The row ID of the newly inserted quiz, or -1 if the insert failed
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz): Long
    
    /**
     * Delete a quiz from the database
     * @param quiz The quiz to be deleted
     */
    @Delete
    suspend fun deleteQuiz(quiz: Quiz)
    
    /**
     * Update the completion status of a quiz
     * @param quizId The unique identifier of the quiz
     * @param completed Whether the quiz is completed
     */
    @Query("UPDATE quizzes SET isCompleted = :completed WHERE id = :quizId")
    suspend fun updateQuizCompletionStatus(quizId: Int, completed: Boolean)
}
