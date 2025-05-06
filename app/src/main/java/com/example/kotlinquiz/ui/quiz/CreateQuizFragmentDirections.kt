package com.example.kotlinquiz.ui.quiz

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.kotlinquiz.R
import kotlin.Int

/**
 * Directions for navigating from CreateQuizFragment
 */
class CreateQuizFragmentDirections private constructor() {
    /**
     * Action from CreateQuizFragment to CreateQuestionFragment
     */
    data class ActionCreateQuizFragmentToCreateQuestionFragment(val quizId: Int) : NavDirections {
        override fun getActionId(): Int = R.id.action_createQuizFragment_to_createQuestionFragment

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putInt("quizId", this.quizId)
            return result
        }
    }

    companion object {
        /**
         * Navigate from CreateQuizFragment to CreateQuestionFragment
         */
        fun actionCreateQuizFragmentToCreateQuestionFragment(quizId: Int): NavDirections =
            ActionCreateQuizFragmentToCreateQuestionFragment(quizId)
    }
}
