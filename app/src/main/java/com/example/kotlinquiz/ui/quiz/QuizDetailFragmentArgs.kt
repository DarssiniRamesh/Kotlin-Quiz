package com.example.kotlinquiz.ui.quiz

import androidx.navigation.NavArgs

/**
 * Arguments for QuizDetailFragment
 */
class QuizDetailFragmentArgs(val quizId: Int) : NavArgs {
    companion object {
        @JvmStatic
        fun fromBundle(bundle: android.os.Bundle): QuizDetailFragmentArgs {
            bundle.classLoader = QuizDetailFragmentArgs::class.java.classLoader
            val quizId = bundle.getInt("quizId")
            return QuizDetailFragmentArgs(quizId)
        }
    }
}
