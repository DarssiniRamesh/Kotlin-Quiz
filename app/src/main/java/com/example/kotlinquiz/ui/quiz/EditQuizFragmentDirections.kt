package com.example.kotlinquiz.ui.quiz

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.kotlinquiz.R
import kotlin.Int

/**
 * Directions for navigating from EditQuizFragment
 */
class EditQuizFragmentDirections private constructor() {
    /**
     * Action from EditQuizFragment to CreateQuestionFragment
     */
    data class ActionEditQuizFragmentToCreateQuestionFragment(val quizId: Int) : NavDirections {
        override fun getActionId(): Int = R.id.action_editQuizFragment_to_createQuestionFragment

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putInt("quizId", this.quizId)
            return result
        }
    }

    /**
     * Action from EditQuizFragment to EditQuestionFragment
     */
    data class ActionEditQuizFragmentToEditQuestionFragment(val questionId: Int) : NavDirections {
        override fun getActionId(): Int = R.id.action_editQuizFragment_to_editQuestionFragment

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putInt("questionId", this.questionId)
            return result
        }
    }

    companion object {
        /**
         * Navigate from EditQuizFragment to CreateQuestionFragment
         */
        fun actionEditQuizFragmentToCreateQuestionFragment(quizId: Int): NavDirections =
            ActionEditQuizFragmentToCreateQuestionFragment(quizId)

        /**
         * Navigate from EditQuizFragment to EditQuestionFragment
         */
        fun actionEditQuizFragmentToEditQuestionFragment(questionId: Int): NavDirections =
            ActionEditQuizFragmentToEditQuestionFragment(questionId)
    }
}
