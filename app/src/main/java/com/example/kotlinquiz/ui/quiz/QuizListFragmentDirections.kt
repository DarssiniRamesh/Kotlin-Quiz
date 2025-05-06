package com.example.kotlinquiz.ui.quiz

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.kotlinquiz.R

/**
 * Navigation directions for QuizListFragment
 */
class QuizListFragmentDirections private constructor() {
    companion object {
        /**
         * Navigate from QuizListFragment to QuizDetailFragment
         * @param quizId The ID of the quiz to show details for
         */
        fun actionQuizListFragmentToQuizDetailFragment(quizId: Int): NavDirections {
            return object : NavDirections {
                override val actionId: Int
                    get() = R.id.action_quizListFragment_to_quizDetailFragment
                
                val arguments = mutableMapOf<String, Any>().apply {
                    put("quizId", quizId)
                }
            }
        }
        
        /**
         * Navigate from QuizListFragment to CreateQuizFragment
         */
        fun actionQuizListFragmentToCreateQuizFragment(): NavDirections {
            return ActionOnlyNavDirections(R.id.action_quizListFragment_to_createQuizFragment)
        }
    }
}
