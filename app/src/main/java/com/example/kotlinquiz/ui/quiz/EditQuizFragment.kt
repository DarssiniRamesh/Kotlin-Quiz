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
import androidx.navigation.fragment.navArgs
import com.example.kotlinquiz.R
import com.example.kotlinquiz.data.model.Quiz
import com.example.kotlinquiz.databinding.FragmentCreateQuizBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for editing an existing quiz
 */
@AndroidEntryPoint
class EditQuizFragment : Fragment() {

    private var _binding: FragmentCreateQuizBinding? = null
    private val binding get() = _binding!!

    private val args: EditQuizFragmentArgs by navArgs()
    private val quizViewModel: QuizViewModel by viewModels()
    private var currentQuiz: Quiz? = null

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

        // Load the quiz data
        quizViewModel.loadQuizById(args.quizId)
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.edit_quiz_title)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupClickListeners() {
        binding.buttonSaveQuiz.setOnClickListener {
            if (validateInputs()) {
                updateQuiz()
            }
        }
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

    private fun populateUI(quiz: Quiz) {
        currentQuiz = quiz
        binding.editTextTitle.setText(quiz.title)
        binding.editTextDescription.setText(quiz.description)
        
        // Set difficulty radio button
        when (quiz.difficultyLevel) {
            1 -> binding.radioEasy.isChecked = true
            2 -> binding.radioMedium.isChecked = true
            3 -> binding.radioHard.isChecked = true
        }
        
        // Set time slider
        binding.sliderTime.value = quiz.timeInMinutes.toFloat()
    }

    private fun updateQuiz() {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val difficultyLevel = when (binding.radioGroupDifficulty.checkedRadioButtonId) {
            R.id.radio_easy -> 1
            R.id.radio_medium -> 2
            R.id.radio_hard -> 3
            else -> 1
        }
        val timeInMinutes = binding.sliderTime.value.toInt()

        currentQuiz?.let { quiz ->
            val updatedQuiz = quiz.copy(
                title = title,
                description = description,
                difficultyLevel = difficultyLevel,
                timeInMinutes = timeInMinutes
            )
            quizViewModel.saveQuiz(updatedQuiz)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            quizViewModel.selectedQuizState.collectLatest { quiz ->
                quiz?.let { populateUI(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            quizViewModel.uiState.collectLatest { uiState ->
                binding.progressBar.isVisible = uiState is QuizViewModel.UiState.Loading

                if (uiState is QuizViewModel.UiState.Success) {
                    findNavController().navigateUp()
                } else if (uiState is QuizViewModel.UiState.Error) {
                    Snackbar.make(
                        binding.root,
                        uiState.message,
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
