package com.example.kotlinquiz.utils

import com.example.kotlinquiz.data.model.Question
import com.example.kotlinquiz.data.model.Quiz
import com.example.kotlinquiz.data.repository.QuestionRepository
import com.example.kotlinquiz.data.repository.QuizRepository

/**
 * Utility class to generate sample data for demonstration purposes
 */
class DataGenerator(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository
) {
    /**
     * Generate sample quiz data
     */
    suspend fun generateSampleData() {
        // Create sample quizzes
        val kotlinBasicsQuizId = quizRepository.saveQuiz(
            Quiz(
                title = "Kotlin Basics",
                description = "Test your knowledge of Kotlin fundamentals including syntax, variables, functions, and control flow.",
                difficultyLevel = 1,
                timeInMinutes = 10
            )
        )

        val kotlinAdvancedQuizId = quizRepository.saveQuiz(
            Quiz(
                title = "Advanced Kotlin",
                description = "Challenge yourself with advanced Kotlin topics including coroutines, extensions, and higher-order functions.",
                difficultyLevel = 3,
                timeInMinutes = 15
            )
        )

        val androidBasicsQuizId = quizRepository.saveQuiz(
            Quiz(
                title = "Android Development",
                description = "Test your knowledge of Android app development fundamentals including activities, fragments, and lifecycle.",
                difficultyLevel = 2,
                timeInMinutes = 12
            )
        )

        // Generate questions for Kotlin Basics quiz
        val kotlinBasicsQuestions = listOf(
            Question(
                quizId = kotlinBasicsQuizId.toInt(),
                questionText = "What is the correct way to declare a variable in Kotlin that cannot be reassigned?",
                options = listOf(
