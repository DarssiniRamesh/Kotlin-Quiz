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
import com.example.kotlinquiz.data.model.Question
import com.example.kotlinquiz.databinding.FragmentTakeQuizBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TakeQuizFragment : Fragment() {

    private var _binding: FragmentTakeQuizBinding? = null
    private val binding get() = _binding!!

    private val args: TakeQuizFragmentArgs by navArgs()
    private val quizViewModel: QuizViewModel by viewModels()
    private val questionViewModel: QuestionViewModel by viewModels()

    private var questions = listOf<Question>()
    private var currentQuestionIndex = 0
    private var userAnswers = mutableMapOf<Int, Int>() // Question ID to selected option index
    private var quizSubmitted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTakeQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupClickListeners()
        observeViewModel()

        // Load quiz and questions
        quizViewModel.loadQuizById(args.quizId)
        questionViewModel.loadQuestionsForQuiz(args.quizId)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            confirmExit()
        }
    }

    private fun setupClickListeners() {
        binding.buttonNext.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayCurrentQuestion()
            } else {
                // On last question, show submit button
                showSubmitConfirmation()
            }
        }

        binding.buttonPrevious.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayCurrentQuestion()
            }
        }

        binding.buttonSubmit.setOnClickListener {
            showSubmitConfirmation()
        }

        binding.radioGroupOptions.setOnCheckedChangeListener { _, checkedId ->
            if (!quizSubmitted) {
                val currentQuestion = questions.getOrNull(currentQuestionIndex)
                currentQuestion?.let { question ->
                    // Map the checked radio button ID to the option index
                    val selectedOptionIndex = when (checkedId) {
                        R.id.radio_option_1 -> 0
                        R.id.radio_option_2 -> 1
                        R.id.radio_option_3 -> 2
                        R.id.radio_option_4 -> 3
                        else -> -1
                    }
                    if (selectedOptionIndex != -1) {
                        userAnswers[question.id] = selectedOptionIndex
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            quizViewModel.selectedQuizState.collectLatest { quiz ->
                quiz?.let {
                    binding.toolbar.title = it.title
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            questionViewModel.questionsState.collectLatest { questionList ->
                questions = questionList
                if (questions.isNotEmpty()) {
                    displayCurrentQuestion()
                }
                binding.progressBar.isVisible = false
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            questionViewModel.uiState.collectLatest { uiState ->
                binding.progressBar.isVisible = uiState is QuestionViewModel.UiState.Loading

                if (uiState is QuestionViewModel.UiState.Error) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Error")
                        .setMessage(getString(R.string.error_loading_questions))
                        .setPositiveButton("OK") { _, _ ->
                            findNavController().navigateUp()
                        }
                        .show()
                }
            }
        }
    }

    private fun displayCurrentQuestion() {
        if (questions.isEmpty()) return

        val currentQuestion = questions.getOrNull(currentQuestionIndex) ?: return
        
        // Update progress text
        binding.textProgress.text = getString(
            R.string.quiz_progress,
            currentQuestionIndex + 1,
            questions.size
        )
        
        // Update question text
        binding.textQuestion.text = currentQuestion.questionText
        
        // Clear existing options
        binding.radioGroupOptions.clearCheck()
        
        // Set up options
        if (currentQuestion.options.size >= 1) {
            binding.radioOption1.text = currentQuestion.options[0]
            binding.radioOption1.isVisible = true
        } else {
            binding.radioOption1.isVisible = false
        }
        
        if (currentQuestion.options.size >= 2) {
            binding.radioOption2.text = currentQuestion.options[1]
            binding.radioOption2.isVisible = true
        } else {
            binding.radioOption2.isVisible = false
        }
        
        if (currentQuestion.options.size >= 3) {
            binding.radioOption3.text = currentQuestion.options[2]
            binding.radioOption3.isVisible = true
        } else {
            binding.radioOption3.isVisible = false
        }
        
        if (currentQuestion.options.size >= 4) {
            binding.radioOption4.text = currentQuestion.options[3]
            binding.radioOption4.isVisible = true
        } else {
            binding.radioOption4.isVisible = false
        }
        
        // Check the user's answer if they've already answered this question
        userAnswers[currentQuestion.id]?.let { selectedOptionIndex ->
            when (selectedOptionIndex) {
                0 -> binding.radioOption1.isChecked = true
                1 -> binding.radioOption2.isChecked = true
                2 -> binding.radioOption3.isChecked = true
                3 -> binding.radioOption4.isChecked = true
            }
        }
        
        // Update navigation buttons
        binding.buttonPrevious.isEnabled = currentQuestionIndex > 0
        binding.buttonNext.isEnabled = currentQuestionIndex < questions.size - 1
        
        // Show/hide submit button
        binding.buttonSubmit.isVisible = true
    }

    private fun showSubmitConfirmation() {
        val answeredQuestions = userAnswers.size
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Submit Quiz")
            .setMessage("You have answered $answeredQuestions out of ${questions.size} questions. Are you sure you want to submit the quiz?")
            .setPositiveButton("Submit") { _, _ ->
                submitQuiz()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun submitQuiz() {
        viewLifecycleOwner.lifecycleScope.launch {
            quizSubmitted = true
            
            // Submit answers and get quiz attempt ID
            val quizAttemptId = questionViewModel.submitQuizAnswers(
                quizId = args.quizId,
                answers = userAnswers.map { (questionId, selectedOption) ->
                    QuestionViewModel.AnswerSubmission(
                        questionId = questionId,
                        selectedOptionIndex = selectedOption
                    )
                }
            )
            
            // Navigate to results screen
            findNavController().navigate(
                TakeQuizFragmentDirections.actionTakeQuizFragmentToQuizResultFragment(quizAttemptId)
            )
        }
    }

    private fun confirmExit() {
        if (!quizSubmitted && userAnswers.isNotEmpty()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Exit Quiz")
                .setMessage("Your progress will be lost. Are you sure you want to exit?")
                .setPositiveButton("Exit") { _, _ ->
                    findNavController().navigateUp()
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
