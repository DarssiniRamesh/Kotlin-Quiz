package com.example.kotlinquiz.ui.question

import android.os.Bundle
import androidx.navigation.NavArgs
import kotlin.Int
import kotlin.jvm.JvmStatic

/**
 * Navigation arguments for the CreateQuestionFragment
 */
data class CreateQuestionFragmentArgs(
    val quizId: Int
) : NavArgs {
    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): CreateQuestionFragmentArgs {
            bundle.classLoader = CreateQuestionFragmentArgs::class.java.classLoader
            val __quizId = bundle.getInt("quizId")
            return CreateQuestionFragmentArgs(__quizId)
        }
    }
}
