package com.example.kotlinquiz.di

import android.content.Context
import com.example.kotlinquiz.data.local.*
import com.example.kotlinquiz.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing application-level dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Provide the Room database instance
     * @param context The application context
     * @return A singleton instance of the QuizDatabase
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuizDatabase {
        return QuizDatabase.getInstance(context)
    }
    
    /**
     * Provide the QuizDao instance
     * @param database The QuizDatabase
     * @return The QuizDao
     */
    @Provides
    fun provideQuizDao(database: QuizDatabase): QuizDao {
        return database.quizDao()
    }
    
    /**
     * Provide the QuestionDao instance
     * @param database The QuizDatabase
     * @return The QuestionDao
     */
    @Provides
    fun provideQuestionDao(database: QuizDatabase): QuestionDao {
        return database.questionDao()
    }
    
    /**
     * Provide the CategoryDao instance
     * @param database The QuizDatabase
     * @return The CategoryDao
     */
    @Provides
    fun provideCategoryDao(database: QuizDatabase): CategoryDao {
        return database.categoryDao()
    }
    
    /**
     * Provide the QuizAttemptDao instance
     * @param database The QuizDatabase
     * @return The QuizAttemptDao
     */
    @Provides
    fun provideQuizAttemptDao(database: QuizDatabase): QuizAttemptDao {
        return database.quizAttemptDao()
    }
    
    /**
     * Provide the AnswerDao instance
     * @param database The QuizDatabase
     * @return The AnswerDao
     */
    @Provides
    fun provideAnswerDao(database: QuizDatabase): AnswerDao {
        return database.answerDao()
    }
    
    /**
     * Provide the QuizRepository implementation
     * @param quizDao The QuizDao
     * @return An implementation of QuizRepository
     */
    @Provides
    @Singleton
    fun provideQuizRepository(quizDao: QuizDao): QuizRepository {
        return QuizRepositoryImpl(quizDao)
    }
    
    /**
     * Provide the QuestionRepository implementation
     * @param questionDao The QuestionDao
     * @return An implementation of QuestionRepository
     */
    @Provides
    @Singleton
    fun provideQuestionRepository(questionDao: QuestionDao): QuestionRepository {
        return QuestionRepositoryImpl(questionDao)
    }
    
    /**
     * Provide the CategoryRepository implementation
     * @param categoryDao The CategoryDao
     * @return An implementation of CategoryRepository
     */
    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao)
    }
    
    /**
     * Provide the QuizAttemptRepository implementation
     * @param quizAttemptDao The QuizAttemptDao
     * @return An implementation of QuizAttemptRepository
     */
    @Provides
    @Singleton
    fun provideQuizAttemptRepository(quizAttemptDao: QuizAttemptDao): QuizAttemptRepository {
        return QuizAttemptRepositoryImpl(quizAttemptDao)
    }
    
    /**
     * Provide the AnswerRepository implementation
     * @param answerDao The AnswerDao
     * @return An implementation of AnswerRepository
     */
    @Provides
    @Singleton
    fun provideAnswerRepository(answerDao: AnswerDao): AnswerRepository {
        return AnswerRepositoryImpl(answerDao)
    }
    
    /**
     * Provide the UserPreferences instance
     * @param context The application context
     * @return A singleton instance of UserPreferences
     */
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}
