package com.example.kotlinquiz.ui.quiz

import androidx.navigation.NavDirections
import com.example.kotlinquiz.R

/**
 * Navigation directions for QuizDetailFragment
 */
class QuizDetailFragmentDirections private constructor() {
    companion object {
        /**
         * Navigate from QuizDetailFragment to TakeQuizFragment
         * @param quizId The ID of the quiz to take
         */
        fun actionQuizDetailFragmentToTakeQuizFragment(quizId: Int): NavDirections {
            return object : NavDirections {
                override val actionId: Int
                    get() = R.id.action_quizDetailFragment_to_takeQuizFragment
                
                val arguments = mutableMapOf<String, Any>().apply {
                    put("quizId", quizId)
                }
            }
        }
        
        /**
         * Navigate from QuizDetailFragment to EditQuizFragment
         * @param quizId The ID of the quiz to edit
         */
        fun actionQuizDetailFragmentToEditQuizFragment(quizId: Int): NavDirections {
            return object : NavDirections {
                override val actionId: Int
                    get() = R.id.action_quizDetailFragment_to_editQuizFragment
                
                val arguments = mutableMapOf<String, Any>().apply {
                    put("quizId", quizId)
                }
            }
        }
    }
}
