package com.example.kotlinquiz.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquiz.databinding.ItemQuizResultAnswerBinding

class QuizResultAnswerAdapter : ListAdapter<QuizAnswerResult, QuizResultAnswerAdapter.ViewHolder>(
    QuizAnswerDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuizResultAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemQuizResultAnswerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuizAnswerResult) {
            binding.apply {
                textQuestion.text = item.question
                textYourAnswer.text = "Your answer: ${item.yourAnswer}"
                textCorrectAnswer.text = "Correct answer: ${item.correctAnswer}"
                textExplanation.text = item.explanation

                if (item.isCorrect) {
                    textYourAnswer.setTextColor(root.context.getColor(android.R.color.holo_green_dark))
                } else {
                    textYourAnswer.setTextColor(root.context.getColor(android.R.color.holo_red_dark))
                }
            }
        }
    }

    private class QuizAnswerDiffCallback : DiffUtil.ItemCallback<QuizAnswerResult>() {
        override fun areItemsTheSame(oldItem: QuizAnswerResult, newItem: QuizAnswerResult): Boolean {
            return oldItem.question == newItem.question
        }

        override fun areContentsTheSame(oldItem: QuizAnswerResult, newItem: QuizAnswerResult): Boolean {
            return oldItem == newItem
        }
    }
}
