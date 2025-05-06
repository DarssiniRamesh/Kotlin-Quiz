package com.example.kotlinquiz.ui.question

import android.os.Bundle
import androidx.navigation.NavArgs
import kotlin.Int
import kotlin.jvm.JvmStatic

/**
 * Navigation arguments for the EditQuestionFragment
 */
data class EditQuestionFragmentArgs(
    val questionId: Int
) : NavArgs {
    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): EditQuestionFragmentArgs {
            bundle.classLoader = EditQuestionFragmentArgs::class.java.classLoader
            val __questionId = bundle.getInt("questionId")
            return EditQuestionFragmentArgs(__questionId)
        }
    }
}
