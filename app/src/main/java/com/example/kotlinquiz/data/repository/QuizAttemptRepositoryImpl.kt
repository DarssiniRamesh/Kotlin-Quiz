package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.local.QuizAttemptDao
import com.example.kotlinquiz.data.model.QuizAttemptEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

/**
 * Implementation of QuizAttemptRepository that uses Room database for local storage
 * @param quizAttemptDao The DAO for quiz attempt-related database operations
 */
class QuizAttemptRepositoryImpl @Inject constructor(
    private val quizAttemptDao: QuizAttemptDao
) : QuizAttemptRepository {
    
    override fun getAttemptsForQuiz(quizId: Int): Flow<List<QuizAttemptEntity>> {
        return quizAttemptDao.getAttemptsForQuiz(quizId)
    }
    
    override fun getCompletedAttempts(): Flow<List<QuizAttemptEntity>> {
        return quizAttemptDao.getCompletedAttempts()
    }
    
    override suspend fun getQuizAttemptById(quizAttemptId: Int): QuizAttemptEntity? {
        return quizAttemptDao.getQuizAttemptById(quizAttemptId)
    }
    
    override suspend fun getLatestAttemptForQuiz(quizId: Int): QuizAttemptEntity? {
        return quizAttemptDao.getLatestAttemptForQuiz(quizId)
    }
    
    override suspend fun createQuizAttempt(quizAttempt: QuizAttemptEntity): Long {
        return quizAttemptDao.insertQuizAttempt(quizAttempt)
    }
    
    override suspend fun updateQuizAttempt(quizAttempt: QuizAttemptEntity) {
        quizAttemptDao.updateQuizAttempt(quizAttempt)
    }
    
    override suspend fun deleteQuizAttempt(quizAttemptId: Int) {
        quizAttemptDao.getQuizAttemptById(quizAttemptId)?.let { attempt ->
            quizAttemptDao.deleteQuizAttempt(attempt)
        }
    }
    
    override fun getQuizPerformanceStats(): Flow<Map<Int, Pair<Double, Int>>> {
        return quizAttemptDao.getQuizPerformanceStats()
    }
    
    override suspend fun getAttemptsInDateRange(startDate: Date, endDate: Date): List<QuizAttemptEntity> {
        return quizAttemptDao.getAttemptsInDateRange(startDate, endDate)
    }
}
