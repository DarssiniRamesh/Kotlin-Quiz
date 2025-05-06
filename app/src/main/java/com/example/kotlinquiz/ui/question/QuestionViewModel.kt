package com.example.kotlinquiz.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.data.model.Question
import com.example.kotlinquiz.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for handling question-related business logic
 */
@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {
    
    // State for holding the list of questions for a quiz
    private val _questionsState = MutableStateFlow<List<Question>>(emptyList())
    val questionsState: StateFlow<List<Question>> = _questionsState.asStateFlow()
    
    // State for holding a specific question
    private val _selectedQuestionState = MutableStateFlow<Question?>(null)
    val selectedQuestionState: StateFlow<Question?> = _selectedQuestionState.asStateFlow()
    
    // State for tracking loading, errors, etc.
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    /**
     * Load all questions for a specific quiz
     * @param quizId The unique identifier for the quiz
     */
    fun loadQuestionsForQuiz(quizId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            questionRepository.getQuestionsForQuiz(quizId)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { questions ->
                    _questionsState.value = questions
                    _uiState.value = UiState.Success
                }
        }
    }
    
    /**
     * Load a specific question by its ID
     * @param questionId The unique identifier for the question
     */
    fun loadQuestionById(questionId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val question = questionRepository.getQuestionById(questionId)
                _selectedQuestionState.value = question
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Add a new question to a quiz
     * @param question The question to add
     */
    fun addQuestion(question: Question) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                questionRepository.addQuestion(question)
                _uiState.value = UiState.Success
                loadQuestionsForQuiz(question.quizId)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Add multiple questions to a quiz
     * @param questions The list of questions to add
     */
    fun addQuestions(questions: List<Question>) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                questionRepository.addQuestions(questions)
                _uiState.value = UiState.Success
                if (questions.isNotEmpty()) {
                    loadQuestionsForQuiz(questions.first().quizId)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Update an existing question
     * @param question The question to update
     */
    fun updateQuestion(question: Question) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                questionRepository.updateQuestion(question)
                _uiState.value = UiState.Success
                loadQuestionsForQuiz(question.quizId)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Delete a question by its ID
     * @param questionId The unique identifier for the question
     * @param quizId The unique identifier for the quiz (needed to refresh the list)
     */
    fun deleteQuestion(questionId: Int, quizId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                questionRepository.deleteQuestion(questionId)
                _uiState.value = UiState.Success
                loadQuestionsForQuiz(quizId)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Represents the UI state for the question screens
     */
    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
