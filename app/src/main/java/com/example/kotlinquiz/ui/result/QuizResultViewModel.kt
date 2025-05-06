package com.example.kotlinquiz.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.data.repository.QuizAttemptRepository
import com.example.kotlinquiz.data.repository.AnswerRepository
import com.example.kotlinquiz.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizResultViewModel @Inject constructor(
    private val quizAttemptRepository: QuizAttemptRepository,
    private val answerRepository: AnswerRepository,
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _quizResultState = MutableStateFlow<QuizResultState>(QuizResultState.Loading)
    val quizResultState: StateFlow<QuizResultState> = _quizResultState

    fun loadQuizResults(quizAttemptId: Long) {
        viewModelScope.launch {
            try {
                val attempt = quizAttemptRepository.getQuizAttemptById(quizAttemptId)
                val answers = answerRepository.getAnswersForQuizAttempt(quizAttemptId)
                val questions = questionRepository.getQuestionsForQuiz(attempt.quizId)

                // Calculate statistics
                val totalQuestions = questions.size
                val correctAnswers = answers.count { it.isCorrect }
                val score = (correctAnswers.toFloat() / totalQuestions * 100).toInt()
                val timeTaken = attempt.endTime.time - attempt.startTime.time

                // Create detailed answer list
                val detailedAnswers = answers.map { answer ->
                    val question = questions.find { it.id == answer.questionId }!!
                    QuizAnswerResult(
                        question = question.text,
                        yourAnswer = question.options[answer.selectedOptionIndex],
                        correctAnswer = question.options[question.correctOptionIndex],
                        isCorrect = answer.isCorrect,
                        explanation = question.explanation ?: ""
                    )
                }

                val result = QuizResult(
                    score = score,
                    timeTaken = timeTaken,
                    totalQuestions = totalQuestions,
                    correctAnswers = correctAnswers,
                    incorrectAnswers = totalQuestions - correctAnswers,
                    answers = detailedAnswers
                )

                _quizResultState.update { QuizResultState.Success(result) }
            } catch (e: Exception) {
                _quizResultState.update { QuizResultState.Error(e.message ?: "Error loading quiz results") }
            }
        }
    }
}

data class QuizResult(
    val score: Int,
    val timeTaken: Long,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val answers: List<QuizAnswerResult>
)

data class QuizAnswerResult(
    val question: String,
    val yourAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val explanation: String
)

sealed class QuizResultState {
    object Loading : QuizResultState()
    data class Success(val quizResult: QuizResult) : QuizResultState()
    data class Error(val message: String) : QuizResultState()
}
