package com.example.kotlinquiz.ui.quiz

import androidx.navigation.NavArgs

/**
 * Arguments for EditQuizFragment
 */
class EditQuizFragmentArgs(val quizId: Int) : NavArgs {
    companion object {
        @JvmStatic
        fun fromBundle(bundle: android.os.Bundle): EditQuizFragmentArgs {
            bundle.classLoader = EditQuizFragmentArgs::class.java.classLoader
            val quizId = bundle.getInt("quizId")
            return EditQuizFragmentArgs(quizId)
        }
    }
}
