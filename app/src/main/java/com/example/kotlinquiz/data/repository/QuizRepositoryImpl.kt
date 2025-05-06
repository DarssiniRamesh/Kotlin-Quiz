package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.local.QuizDao
import com.example.kotlinquiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of QuizRepository that uses Room database for local storage
 * @param quizDao The DAO for quiz-related database operations
 */
class QuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao
) : QuizRepository {
    
    override fun getAllQuizzes(): Flow<List<Quiz>> {
        return quizDao.getAllQuizzes()
    }
    
    override suspend fun getQuizById(quizId: Int): Quiz? {
        return quizDao.getQuizById(quizId)
    }
    
    override suspend fun saveQuiz(quiz: Quiz): Long {
        return quizDao.insertQuiz(quiz)
    }
    
    override suspend fun deleteQuiz(quizId: Int) {
        quizDao.getQuizById(quizId)?.let { quiz ->
            quizDao.deleteQuiz(quiz)
        }
    }
    
    override suspend fun markQuizAsCompleted(quizId: Int, completed: Boolean) {
        quizDao.updateQuizCompletionStatus(quizId, completed)
    }
}
