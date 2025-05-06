package com.example.kotlinquiz.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinquiz.R
import com.example.kotlinquiz.data.model.Question
import com.example.kotlinquiz.databinding.FragmentEditQuestionBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for editing an existing quiz question
 */
@AndroidEntryPoint
class EditQuestionFragment : Fragment() {

    private var _binding: FragmentEditQuestionBinding? = null
    private val binding get() = _binding!!
    
    private val questionViewModel: QuestionViewModel by viewModels()
    private val args: EditQuestionFragmentArgs by navArgs()
    
    private lateinit var optionAdapter: QuestionOptionAdapter
    private var currentQuestion: Question? = null
    
    companion object {
        private const val MIN_OPTIONS = 2
        private const val MAX_OPTIONS = 8
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        
        // Load question data
        questionViewModel.loadQuestionById(args.questionId)
    }
    
    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.edit_question_title)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
    
    private fun setupRecyclerView() {
        optionAdapter = QuestionOptionAdapter()
        binding.recyclerViewOptions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = optionAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.buttonAddOption.setOnClickListener {
            if (optionAdapter.itemCount < MAX_OPTIONS) {
                optionAdapter.addOption()
                updateCorrectAnswerRadioGroup()
            } else {
                showSnackbar(getString(R.string.max_options_reached))
            }
        }
        
        binding.buttonRemoveOption.setOnClickListener {
            if (optionAdapter.itemCount > MIN_OPTIONS) {
                optionAdapter.removeLastOption()
                updateCorrectAnswerRadioGroup()
            } else {
                showSnackbar(getString(R.string.min_options_required))
            }
        }
        
        binding.buttonSaveQuestion.setOnClickListener {
            if (validateInputs()) {
                saveQuestion()
            }
        }
    }
    
    private fun populateUI(question: Question) {
        currentQuestion = question
        
        // Set question text
        binding.editTextQuestion.setText(question.questionText)
        
        // Set options
        optionAdapter.updateOptions(question.options)
        
        // Update radio group for correct answer
        updateCorrectAnswerRadioGroup()
        
        // Select the correct answer
        selectCorrectAnswer(question.correctAnswerIndex)
        
        // Set explanation
        binding.editTextExplanation.setText(question.explanation)
    }
    
    private fun updateCorrectAnswerRadioGroup() {
        binding.radioGroupCorrectAnswer.removeAllViews()
        
        // Add radio buttons for each option
        for (i in 0 until optionAdapter.itemCount) {
            val radioButton = RadioButton(context).apply {
                id = View.generateViewId()
                text = getString(R.string.option_hint, i + 1)
            }
            binding.radioGroupCorrectAnswer.addView(radioButton)
        }
    }
    
    private fun selectCorrectAnswer(index: Int) {
        if (index < binding.radioGroupCorrectAnswer.childCount) {
            val radioButton = binding.radioGroupCorrectAnswer.getChildAt(index) as RadioButton
            radioButton.isChecked = true
        }
    }
    
    private fun validateInputs(): Boolean {
        var isValid = true
        
        // Validate question text
        if (binding.editTextQuestion.text.isNullOrBlank()) {
            binding.textInputLayoutQuestion.error = "Question text cannot be empty"
            isValid = false
        } else {
            binding.textInputLayoutQuestion.error = null
        }
        
        // Validate options
        val options = optionAdapter.getOptions()
        for ((index, option) in options.withIndex()) {
            if (option.isBlank()) {
                showSnackbar("Option ${index + 1} cannot be empty")
                isValid = false
                break
            }
        }
        
        // Ensure a correct answer is selected
        if (binding.radioGroupCorrectAnswer.checkedRadioButtonId == -1) {
            showSnackbar("Please select a correct answer")
            isValid = false
        }
        
        return isValid
    }
    
    private fun getCorrectAnswerIndex(): Int {
        val checkedId = binding.radioGroupCorrectAnswer.checkedRadioButtonId
        for (i in 0 until binding.radioGroupCorrectAnswer.childCount) {
            val radioButton = binding.radioGroupCorrectAnswer.getChildAt(i) as RadioButton
            if (radioButton.id == checkedId) {
                return i
            }
        }
        return 0 // Default to first option if none selected
    }
    
    private fun saveQuestion() {
        currentQuestion?.let { question ->
            val updatedQuestion = question.copy(
                questionText = binding.editTextQuestion.text.toString().trim(),
                options = optionAdapter.getOptions(),
                correctAnswerIndex = getCorrectAnswerIndex(),
                explanation = binding.editTextExplanation.text.toString().trim()
            )
            
            questionViewModel.updateQuestion(updatedQuestion)
        }
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            questionViewModel.selectedQuestionState.collectLatest { question ->
                question?.let { populateUI(it) }
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            questionViewModel.uiState.collectLatest { uiState ->
                binding.progressBar.isVisible = uiState is QuestionViewModel.UiState.Loading
                
                when (uiState) {
                    is QuestionViewModel.UiState.Success -> {
                        findNavController().navigateUp()
                    }
                    is QuestionViewModel.UiState.Error -> {
                        showSnackbar(getString(R.string.error_updating_question))
                    }
                    else -> { /* No action needed for other states */ }
                }
            }
        }
    }
    
    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
