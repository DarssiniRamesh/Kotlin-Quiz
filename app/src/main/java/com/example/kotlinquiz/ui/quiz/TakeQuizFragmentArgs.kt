package com.example.kotlinquiz.ui.quiz

import androidx.navigation.NavArgs

/**
 * Arguments for TakeQuizFragment
 */
class TakeQuizFragmentArgs(val quizId: Int) : NavArgs {
    companion object {
        @JvmStatic
        fun fromBundle(bundle: android.os.Bundle): TakeQuizFragmentArgs {
            bundle.classLoader = TakeQuizFragmentArgs::class.java.classLoader
            val quizId = bundle.getInt("quizId")
            return TakeQuizFragmentArgs(quizId)
        }
    }
}
