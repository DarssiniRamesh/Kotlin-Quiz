package com.example.kotlinquiz.data.util

import com.example.kotlinquiz.data.model.CategoryEntity
import com.example.kotlinquiz.data.model.Question
import com.example.kotlinquiz.data.model.Quiz
import com.example.kotlinquiz.data.repository.CategoryRepository
import com.example.kotlinquiz.data.repository.QuestionRepository
import com.example.kotlinquiz.data.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for generating sample quiz data
 */
@Singleton
class QuizDataGenerator @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository
) {
    /**
     * Generate sample quizzes and questions if the database is empty
     */
    fun generateSampleDataIfNeeded() {
        CoroutineScope(Dispatchers.IO).launch {
            // Check if we already have quizzes
            val existingQuizzes = quizRepository.getAllQuizzes().first()
            if (existingQuizzes.isEmpty()) {
                generateSampleData()
            }
        }
    }

    /**
     * Generate sample quizzes and questions
     */
    private suspend fun generateSampleData() {
        // Create categories
        val programmingCategory = CategoryEntity(
            name = "Programming Languages",
            description = "Quizzes about various programming languages and their features",
            iconUrl = null
        )
        val programmingCategoryId = categoryRepository.addCategory(programmingCategory).toInt()
        
        val androidCategory = CategoryEntity(
            name = "Android Development",
            description = "Quizzes focused on Android app development concepts and practices",
            iconUrl = null
        )
        val androidCategoryId = categoryRepository.addCategory(androidCategory).toInt()
        
        val kotlinCategory = CategoryEntity(
            name = "Kotlin",
            description = "In-depth quizzes about the Kotlin programming language",
            iconUrl = null
        )
        val kotlinCategoryId = categoryRepository.addCategory(kotlinCategory).toInt()

        // Create Kotlin quiz
        val kotlinQuiz = Quiz(
            title = "Kotlin Basics",
            description = "Test your knowledge of Kotlin programming language fundamentals",
            categoryId = kotlinCategoryId,
            difficultyLevel = 1,
            timeInMinutes = 10
        )
        val kotlinQuizId = quizRepository.saveQuiz(kotlinQuiz).toInt()

        // Create Android quiz
        val androidQuiz = Quiz(
            title = "Android Development",
            description = "Test your knowledge of Android application development concepts",
            categoryId = androidCategoryId,
            difficultyLevel = 2,
            timeInMinutes = 15
        )
        val androidQuizId = quizRepository.saveQuiz(androidQuiz).toInt()

        // Create Advanced Kotlin quiz
        val advancedKotlinQuiz = Quiz(
            title = "Advanced Kotlin",
            description = "Challenge yourself with advanced Kotlin programming concepts",
            categoryId = kotlinCategoryId,
            difficultyLevel = 3,
            timeInMinutes = 20
        )
        val advancedKotlinQuizId = quizRepository.saveQuiz(advancedKotlinQuiz).toInt()
        
        // Create general programming quiz
        val programmingQuiz = Quiz(
            title = "Programming Concepts",
            description = "Test your knowledge of general programming concepts and paradigms",
            categoryId = programmingCategoryId,
            difficultyLevel = 2,
            timeInMinutes = 15
        )
        val programmingQuizId = quizRepository.saveQuiz(programmingQuiz).toInt()

        // Add questions to Kotlin quiz
        val kotlinQuestions = listOf(
            Question(
                quizId = kotlinQuizId,
                questionText = "What is the Kotlin file extension?",
                options = listOf(".kt", ".kotlin", ".kl", ".ktn"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = kotlinQuizId,
                questionText = "Which of the following is used to declare a variable that cannot be reassigned?",
                options = listOf("val", "var", "const", "final"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = kotlinQuizId,
                questionText = "In Kotlin, how do you check if a variable is null?",
                options = listOf("variable == null", "variable.isNull()", "variable === null", "variable?.isEmpty()"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = kotlinQuizId,
                questionText = "Which function is the entry point of a Kotlin program?",
                options = listOf("main()", "start()", "init()", "run()"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = kotlinQuizId,
                questionText = "What does the '?:' operator do in Kotlin?",
                options = listOf(
                    "Returns the left value if not null, otherwise returns the right value",
                    "Checks if two values are equal",
                    "Performs a null-safe cast",
                    "Checks if a value is within a range"
                ),
                correctAnswerIndex = 0
            )
        )
        questionRepository.addQuestions(kotlinQuestions)

        // Add questions to Android quiz
        val androidQuestions = listOf(
            Question(
                quizId = androidQuizId,
                questionText = "Which file contains the app's package name and declarations of its components?",
                options = listOf("AndroidManifest.xml", "build.gradle", "R.java", "settings.xml"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = androidQuizId,
                questionText = "Which component is NOT one of the four main Android app components?",
                options = listOf("Fragment", "Activity", "Service", "Content Provider"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = androidQuizId,
                questionText = "Which layout is best for complex hierarchies?",
                options = listOf("ConstraintLayout", "LinearLayout", "RelativeLayout", "FrameLayout"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = androidQuizId,
                questionText = "What is the purpose of the ViewModel class in Android?",
                options = listOf(
                    "To store and manage UI-related data in a lifecycle-conscious way",
                    "To handle network requests",
                    "To store persistent data",
                    "To define the layout of UI elements"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = androidQuizId,
                questionText = "What is the main purpose of an Intent in Android?",
                options = listOf(
                    "To communicate between components",
                    "To define the layout of UI elements",
                    "To store app data",
                    "To manage threads"
                ),
                correctAnswerIndex = 0
            )
        )
        questionRepository.addQuestions(androidQuestions)

        // Add questions to Advanced Kotlin quiz
        val advancedKotlinQuestions = listOf(
            Question(
                quizId = advancedKotlinQuizId,
                questionText = "What is a coroutine in Kotlin?",
                options = listOf(
                    "A lightweight thread that can perform asynchronous operations",
                    "A type of function that returns multiple values",
                    "A special container for data classes",
                    "A tool for database operations"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = advancedKotlinQuizId,
                questionText = "Which of the following is NOT a Kotlin scope function?",
                options = listOf("forEach", "apply", "run", "also"),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = advancedKotlinQuizId,
                questionText = "What is the difference between 'lateinit' and 'lazy' in Kotlin?",
                options = listOf(
                    "lateinit is mutable and doesn't require initialization, lazy is read-only and initialized once when accessed",
                    "lazy is mutable and doesn't require initialization, lateinit is read-only and initialized once when accessed",
                    "lateinit is for primitive types, lazy is for objects",
                    "lazy is for primitive types, lateinit is for objects"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = advancedKotlinQuizId,
                questionText = "What is a sealed class in Kotlin?",
                options = listOf(
                    "A class with a restricted class hierarchy",
                    "A class that cannot be instantiated",
                    "A class with private constructor",
                    "A final class that cannot be extended"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = advancedKotlinQuizId,
                questionText = "What does the 'inline' keyword do in Kotlin?",
                options = listOf(
                    "It tells the compiler to copy the function body at the call site",
                    "It makes a function a single line of code",
                    "It indicates that a function cannot have a return value",
                    "It restricts a function to local scope"
                ),
                correctAnswerIndex = 0
            )
        )
        questionRepository.addQuestions(advancedKotlinQuestions)
        
        // Add questions to Programming quiz
        val programmingQuestions = listOf(
            Question(
                quizId = programmingQuizId,
                questionText = "Which of the following is NOT a programming paradigm?",
                options = listOf(
                    "Structural Programming",
                    "Object-Oriented Programming",
                    "Functional Programming",
                    "Procedural Programming"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = programmingQuizId,
                questionText = "What is a pure function?",
                options = listOf(
                    "A function that has no side effects and always returns the same output for the same input",
                    "A function that contains no loops",
                    "A function that is written in a functional language",
                    "A function that takes no parameters"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = programmingQuizId,
                questionText = "What is polymorphism in OOP?",
                options = listOf(
                    "The ability of different objects to respond to the same method in different ways",
                    "The concept of hiding implementation details",
                    "The process of creating a new class from an existing one",
                    "The bundling of data and methods that operate on that data"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = programmingQuizId,
                questionText = "What is recursion?",
                options = listOf(
                    "A technique where a function calls itself",
                    "A loop that never terminates",
                    "A data structure that contains references to itself",
                    "A compiler optimization technique"
                ),
                correctAnswerIndex = 0
            ),
            Question(
                quizId = programmingQuizId,
                questionText = "Which data structure uses LIFO (Last In, First Out) principle?",
                options = listOf(
                    "Stack",
                    "Queue",
                    "Linked List",
                    "Tree"
                ),
                correctAnswerIndex = 0
            )
        )
        questionRepository.addQuestions(programmingQuestions)
    }
}
