package com.example.kotlinquiz.data.repository

import com.example.kotlinquiz.data.local.QuestionDao
import com.example.kotlinquiz.data.model.Question
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of QuestionRepository that uses Room database for local storage
 * @param questionDao The DAO for question-related database operations
 */
class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao
) : QuestionRepository {
    
    override fun getQuestionsForQuiz(quizId: Int): Flow<List<Question>> {
        return questionDao.getQuestionsForQuiz(quizId)
    }
    
    override suspend fun getQuestionById(questionId: Int): Question? {
        return questionDao.getQuestionById(questionId)
    }
    
    override suspend fun addQuestion(question: Question): Long {
        return questionDao.insertQuestion(question)
    }
    
    override suspend fun addQuestions(questions: List<Question>): List<Long> {
        return questionDao.insertQuestions(questions)
    }
    
    override suspend fun updateQuestion(question: Question) {
        questionDao.updateQuestion(question)
    }
    
    override suspend fun deleteQuestion(questionId: Int) {
        questionDao.getQuestionById(questionId)?.let { question ->
            questionDao.deleteQuestion(question)
        }
    }
    
    override suspend fun deleteQuestionsForQuiz(quizId: Int) {
        questionDao.deleteQuestionsForQuiz(quizId)
    }
}
