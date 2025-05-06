package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.local.AnswerDao
import com.example.kotlinquiz.data.model.AnswerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of AnswerRepository that uses Room database for local storage
 * @param answerDao The DAO for answer-related database operations
 */
class AnswerRepositoryImpl @Inject constructor(
    private val answerDao: AnswerDao
) : AnswerRepository {
    
    override suspend fun getAnswersForAttempt(quizAttemptId: Int): List<AnswerEntity> {
        return answerDao.getAnswersForAttempt(quizAttemptId)
    }
    
    override suspend fun getAnswerForQuestion(questionId: Int, quizAttemptId: Int): AnswerEntity? {
        return answerDao.getAnswerForQuestion(questionId, quizAttemptId)
    }
    
    override suspend fun saveAnswer(answer: AnswerEntity): Long {
        return answerDao.insertAnswer(answer)
    }
    
    override suspend fun saveAnswers(answers: List<AnswerEntity>): List<Long> {
        return answerDao.insertAnswers(answers)
    }
    
    override suspend fun deleteAnswersForAttempt(quizAttemptId: Int) {
        answerDao.deleteAnswersForAttempt(quizAttemptId)
    }
    
    override suspend fun getCorrectAnswerCountForAttempt(quizAttemptId: Int): Int {
        return answerDao.getCorrectAnswerCountForAttempt(quizAttemptId)
    }
    
    override suspend fun getAverageTimeSpentForAttempt(quizAttemptId: Int): Double {
        return answerDao.getAverageTimeSpentForAttempt(quizAttemptId)
    }
    
    override fun getMostDifficultQuestions(): Flow<Map<Int, Int>> {
        return answerDao.getMostDifficultQuestions()
    }
}
