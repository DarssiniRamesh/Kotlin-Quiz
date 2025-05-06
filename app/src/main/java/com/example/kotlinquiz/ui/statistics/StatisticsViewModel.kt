package com.example.kotlinquiz.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.data.model.QuizAttemptEntity
import com.example.kotlinquiz.data.repository.QuizAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val quizAttemptRepository: QuizAttemptRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatisticsUiState>(StatisticsUiState.Loading)
    val uiState: StateFlow<StatisticsUiState> = _uiState

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            combine(
                quizAttemptRepository.getCompletedAttempts(),
                quizAttemptRepository.getQuizPerformanceStats()
            ) { attempts, performanceStats ->
                generateStatistics(attempts, performanceStats)
            }.catch { error ->
                _uiState.value = StatisticsUiState.Error(error.message ?: "Unknown error occurred")
            }.collect { statistics ->
                _uiState.value = StatisticsUiState.Success(statistics)
            }
        }
    }

    private fun generateStatistics(
        attempts: List<QuizAttemptEntity>,
        performanceStats: Map<Int, Pair<Double, Int>>
    ): Statistics {
        if (attempts.isEmpty()) {
            return Statistics(
                totalQuizzesTaken = 0,
                averageScore = 0.0,
                totalTimeTaken = 0L,
                quizPerformance = emptyList(),
                recentAttempts = emptyList()
            )
        }

        val totalQuizzes = attempts.size
        val averageScore = attempts.map { it.score }.average()
        val totalTime = attempts.sumOf { 
            (it.endTime?.time ?: 0L) - (it.startTime?.time ?: 0L) 
        }

        val quizPerformance = performanceStats.map { (quizId, stats) ->
            QuizPerformance(
                quizId = quizId,
                averageScore = stats.first.roundToInt(),
                attemptCount = stats.second
            )
        }.sortedByDescending { it.averageScore }

        val recentAttempts = attempts
            .sortedByDescending { it.endTime }
            .take(5)
            .map { attempt ->
                AttemptSummary(
                    quizId = attempt.quizId,
                    score = attempt.score,
                    date = attempt.endTime ?: Date()
                )
            }

        return Statistics(
            totalQuizzesTaken = totalQuizzes,
            averageScore = averageScore,
            totalTimeTaken = totalTime,
            quizPerformance = quizPerformance,
            recentAttempts = recentAttempts
        )
    }
}

sealed class StatisticsUiState {
    data object Loading : StatisticsUiState()
    data class Success(val statistics: Statistics) : StatisticsUiState()
    data class Error(val message: String) : StatisticsUiState()
}

data class Statistics(
    val totalQuizzesTaken: Int,
    val averageScore: Double,
    val totalTimeTaken: Long,
    val quizPerformance: List<QuizPerformance>,
    val recentAttempts: List<AttemptSummary>
)

data class QuizPerformance(
    val quizId: Int,
    val averageScore: Int,
    val attemptCount: Int
)

data class AttemptSummary(
    val quizId: Int,
    val score: Int,
    val date: Date
)
