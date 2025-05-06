package com.example.kotlinquiz.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquiz.R
import com.example.kotlinquiz.data.model.Quiz
import com.example.kotlinquiz.databinding.ItemQuizBinding

/**
 * Adapter for displaying quiz items in a RecyclerView
 */
class QuizAdapter(private val onQuizClicked: (Quiz) -> Unit) : 
    ListAdapter<Quiz, QuizAdapter.QuizViewHolder>(QuizDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ItemQuizBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(getItem(position), onQuizClicked)
    }

    class QuizViewHolder(
        private val binding: ItemQuizBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz, onQuizClicked: (Quiz) -> Unit) {
            binding.apply {
                // Set the quiz title and description
                quizTitle.text = quiz.title
                quizDescription.text = quiz.description
                
                // Set the quiz difficulty text based on the difficulty level
                quizDifficulty.text = when (quiz.difficultyLevel) {
                    1 -> root.context.getString(R.string.difficulty_easy)
                    2 -> root.context.getString(R.string.difficulty_medium)
                    else -> root.context.getString(R.string.difficulty_hard)
                }
                
                // Set the quiz time
                quizTime.text = root.context.getString(R.string.time_format, quiz.timeInMinutes)
                
                // Set the quiz status
                quizStatus.text = if (quiz.isCompleted) {
                    root.context.getString(R.string.status_completed)
                } else {
                    root.context.getString(R.string.status_incomplete)
                }
                
                // Set click listener
                root.setOnClickListener { onQuizClicked(quiz) }
            }
        }
    }

    /**
     * DiffUtil callback class for efficiently updating the RecyclerView
     */
    private class QuizDiffCallback : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem == newItem
        }
    }
}
