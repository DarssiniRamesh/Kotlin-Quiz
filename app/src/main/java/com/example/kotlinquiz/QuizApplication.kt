package com.example.kotlinquiz

import android.app.Application
import com.example.kotlinquiz.data.util.QuizDataGenerator
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Custom Application class that serves as the entry point for Hilt dependency injection.
 * The @HiltAndroidApp annotation triggers Hilt's code generation.
 */
@HiltAndroidApp
class QuizApplication : Application() {
    
    @Inject
    lateinit var quizDataGenerator: QuizDataGenerator
    
    private val applicationScope = CoroutineScope(Dispatchers.IO)
    
    override fun onCreate() {
        super.onCreate()
        
        // Generate sample data when the app is first installed
        applicationScope.launch {
            quizDataGenerator.generateSampleDataIfNeeded()
        }
    }
}
