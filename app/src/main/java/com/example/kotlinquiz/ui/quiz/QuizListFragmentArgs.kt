package com.example.kotlinquiz.ui.quiz

import androidx.navigation.NavArgs

/**
 * Arguments for QuizListFragment
 */
class QuizListFragmentArgs : NavArgs {
    companion object {
        @JvmStatic
        fun fromBundle(bundle: android.os.Bundle): QuizListFragmentArgs {
            return QuizListFragmentArgs()
        }
    }
}
