package com.example.kotlinquiz.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.data.model.Quiz
import com.example.kotlinquiz.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for handling quiz-related business logic
 */
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {
    
    // State for holding the list of quizzes
    private val _quizListState = MutableStateFlow<List<Quiz>>(emptyList())
    val quizListState: StateFlow<List<Quiz>> = _quizListState.asStateFlow()
    
    // State for holding a specific quiz
    private val _selectedQuizState = MutableStateFlow<Quiz?>(null)
    val selectedQuizState: StateFlow<Quiz?> = _selectedQuizState.asStateFlow()
    
    // State for tracking loading, errors, etc.
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    init {
        loadQuizzes()
    }
    
    /**
     * Load all quizzes from the repository
     */
    fun loadQuizzes() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            quizRepository.getAllQuizzes()
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { quizzes ->
                    _quizListState.value = quizzes
                    _uiState.value = UiState.Success
                }
        }
    }
    
    /**
     * Load a specific quiz by its ID
     * @param quizId The unique identifier for the quiz
     */
    fun loadQuizById(quizId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val quiz = quizRepository.getQuizById(quizId)
                _selectedQuizState.value = quiz
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Save a new quiz or update an existing one
     * @param quiz The quiz to be saved
     */
    fun saveQuiz(quiz: Quiz) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                quizRepository.saveQuiz(quiz)
                _uiState.value = UiState.Success
                loadQuizzes() // Refresh the list
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Delete a quiz by its ID
     * @param quizId The unique identifier for the quiz
     */
    fun deleteQuiz(quizId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                quizRepository.deleteQuiz(quizId)
                _uiState.value = UiState.Success
                loadQuizzes() // Refresh the list
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Mark a quiz as completed
     * @param quizId The unique identifier for the quiz
     * @param completed Whether the quiz is completed
     */
    fun markQuizAsCompleted(quizId: Int, completed: Boolean = true) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                quizRepository.markQuizAsCompleted(quizId, completed)
                _uiState.value = UiState.Success
                loadQuizzes() // Refresh the list
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    /**
     * Represents the UI state for the quiz screens
     */
    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
