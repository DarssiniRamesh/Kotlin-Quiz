package com.example.kotlinquiz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinquiz.data.model.*

/**
 * Room database for the Quiz application
 */
@Database(
    entities = [
        Quiz::class,
        Question::class,
        CategoryEntity::class,
        QuizAttemptEntity::class,
        AnswerEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {
    
    /**
     * Get the DAO for quiz-related database operations
     */
    abstract fun quizDao(): QuizDao
    
    /**
     * Get the DAO for question-related database operations
     */
    abstract fun questionDao(): QuestionDao
    
    /**
     * Get the DAO for category-related database operations
     */
    abstract fun categoryDao(): CategoryDao
    
    /**
     * Get the DAO for quiz attempt-related database operations
     */
    abstract fun quizAttemptDao(): QuizAttemptDao
    
    /**
     * Get the DAO for answer-related database operations
     */
    abstract fun answerDao(): AnswerDao
    
    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null
        
        /**
         * Get the singleton instance of the database
         * @param context The application context
         * @return A singleton instance of the QuizDatabase
         */
        fun getInstance(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                .fallbackToDestructiveMigration() // Drops and recreates tables when schema changes
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // You could pre-populate the database here if needed
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
