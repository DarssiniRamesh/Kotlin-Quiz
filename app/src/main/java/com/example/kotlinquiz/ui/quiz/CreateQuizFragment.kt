package com.example.kotlinquiz.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kotlinquiz.R
import com.example.kotlinquiz.data.model.Quiz
import com.example.kotlinquiz.databinding.FragmentCreateQuizBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for creating a new quiz
 */
@AndroidEntryPoint
class CreateQuizFragment : Fragment() {

    private var _binding: FragmentCreateQuizBinding? = null
    private val binding get() = _binding!!

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.create_quiz_title)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupClickListeners() {
        binding.buttonSaveQuiz.setOnClickListener {
            if (validateInputs()) {
                saveQuiz()
            }
        }
        
        binding.buttonAddQuestion.setOnClickListener {
            if (validateInputs()) {
                saveQuizAndAddQuestion()
            }
        }
    }
    
    private fun saveQuizAndAddQuestion() {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val difficultyLevel = when (binding.radioGroupDifficulty.checkedRadioButtonId) {
            R.id.radio_easy -> 1
            R.id.radio_medium -> 2
            R.id.radio_hard -> 3
            else -> 1
        }
        val timeInMinutes = binding.sliderTime.value.toInt()

        val quiz = Quiz(
            title = title,
            description = description,
            difficultyLevel = difficultyLevel,
            timeInMinutes = timeInMinutes
        )

        quizViewModel.saveQuizAndNavigateToAddQuestion(quiz)
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Validate title
        if (binding.editTextTitle.text.isNullOrBlank()) {
            binding.textInputLayoutTitle.error = "Title cannot be empty"
            isValid = false
        } else {
            binding.textInputLayoutTitle.error = null
        }

        // Validate description
        if (binding.editTextDescription.text.isNullOrBlank()) {
            binding.textInputLayoutDescription.error = "Description cannot be empty"
            isValid = false
        } else {
            binding.textInputLayoutDescription.error = null
        }

        return isValid
    }

    private fun saveQuiz() {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val difficultyLevel = when (binding.radioGroupDifficulty.checkedRadioButtonId) {
            R.id.radio_easy -> 1
            R.id.radio_medium -> 2
            R.id.radio_hard -> 3
            else -> 1
        }
        val timeInMinutes = binding.sliderTime.value.toInt()

        val quiz = Quiz(
            title = title,
            description = description,
            difficultyLevel = difficultyLevel,
            timeInMinutes = timeInMinutes
        )

        quizViewModel.saveQuiz(quiz)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            quizViewModel.uiState.collectLatest { uiState ->
                binding.progressBar.isVisible = uiState is QuizViewModel.UiState.Loading

                if (uiState is QuizViewModel.UiState.Success) {
                    findNavController().navigateUp()
                } else if (uiState is QuizViewModel.UiState.NavigateToAddQuestion) {
                    val action = CreateQuizFragmentDirections.actionCreateQuizFragmentToCreateQuestionFragment(
                        uiState.quizId
                    )
                    findNavController().navigate(action)
                } else if (uiState is QuizViewModel.UiState.Error) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_creating_quiz),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
