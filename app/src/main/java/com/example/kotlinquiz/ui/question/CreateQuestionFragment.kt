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
import com.example.kotlinquiz.databinding.FragmentCreateQuestionBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for creating a new quiz question
 */
@AndroidEntryPoint
class CreateQuestionFragment : Fragment() {

    private var _binding: FragmentCreateQuestionBinding? = null
    private val binding get() = _binding!!
    
    private val questionViewModel: QuestionViewModel by viewModels()
    private val args: CreateQuestionFragmentArgs by navArgs()
    
    private lateinit var optionAdapter: QuestionOptionAdapter
    
    companion object {
        private const val MIN_OPTIONS = 2
        private const val MAX_OPTIONS = 8
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        
        // Initialize with two empty options
        addInitialOptions()
    }
    
    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.create_question_title)
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
    
    private fun addInitialOptions() {
        // Add the minimum number of options
        repeat(MIN_OPTIONS) {
            optionAdapter.addOption()
        }
        updateCorrectAnswerRadioGroup()
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
    
    private fun updateCorrectAnswerRadioGroup() {
        binding.radioGroupCorrectAnswer.removeAllViews()
        
        // Add radio buttons for each option
        for (i in 0 until optionAdapter.itemCount) {
            val radioButton = RadioButton(context).apply {
                id = View.generateViewId()
                text = getString(R.string.option_hint, i + 1)
                
                // Set the first option as checked by default if no button is checked
                isChecked = i == 0 && binding.radioGroupCorrectAnswer.checkedRadioButtonId == -1
            }
            binding.radioGroupCorrectAnswer.addView(radioButton)
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
        val questionText = binding.editTextQuestion.text.toString().trim()
        val options = optionAdapter.getOptions()
        val correctAnswerIndex = getCorrectAnswerIndex()
        val explanation = binding.editTextExplanation.text.toString().trim()
        
        val question = Question(
            questionText = questionText,
            options = options,
            correctAnswerIndex = correctAnswerIndex,
            explanation = explanation,
            quizId = args.quizId
        )
        
        questionViewModel.addQuestion(question)
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            questionViewModel.uiState.collectLatest { uiState ->
                binding.progressBar.isVisible = uiState is QuestionViewModel.UiState.Loading
                
                when (uiState) {
                    is QuestionViewModel.UiState.Success -> {
                        findNavController().navigateUp()
                    }
                    is QuestionViewModel.UiState.Error -> {
                        showSnackbar(getString(R.string.error_creating_question))
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
