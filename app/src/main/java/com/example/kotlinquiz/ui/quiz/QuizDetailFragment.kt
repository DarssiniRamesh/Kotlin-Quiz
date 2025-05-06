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
import com.example.kotlinquiz.databinding.FragmentQuizDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for displaying the details of a quiz
 */
@AndroidEntryPoint
class QuizDetailFragment : Fragment() {

    private var _binding: FragmentQuizDetailBinding? = null
    private val binding get() = _binding!!

    private val args: QuizDetailFragmentArgs by navArgs()
    private val quizViewModel: QuizViewModel by viewModels()
    private val questionViewModel: QuestionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeViewModel()
        
        // Load quiz data
        quizViewModel.loadQuizById(args.quizId)
        
        // Load questions for the quiz
        questionViewModel.loadQuestionsForQuiz(args.quizId)
    }
    
    private fun setupClickListeners() {
        binding.buttonTakeQuiz.setOnClickListener {
            findNavController().navigate(
                QuizDetailFragmentDirections.actionQuizDetailFragmentToTakeQuizFragment(args.quizId)
            )
        }
        
        binding.buttonEditQuiz.setOnClickListener {
            findNavController().navigate(
                QuizDetailFragmentDirections.actionQuizDetailFragmentToEditQuizFragment(args.quizId)
            )
        }
        
        binding.buttonDeleteQuiz.setOnClickListener {
            quizViewModel.deleteQuiz(args.quizId)
            findNavController().navigateUp()
        }
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Observe selected quiz
            quizViewModel.selectedQuizState.collectLatest { quiz ->
                quiz?.let {
                    binding.textTitle.text = it.title
                    binding.textDescription.text = it.description
                    binding.textDifficulty.text = when (it.difficultyLevel) {
                        1 -> getString(R.string.difficulty_easy)
                        2 -> getString(R.string.difficulty_medium)
                        else -> getString(R.string.difficulty_hard)
                    }
                    binding.textTime.text = getString(R.string.time_format, it.timeInMinutes)
                    binding.chipStatus.text = if (it.isCompleted) {
                        getString(R.string.status_completed)
                    } else {
                        getString(R.string.status_incomplete)
                    }
                }
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            // Observe questions for the quiz
            questionViewModel.questionsState.collectLatest { questions ->
                binding.textQuestionCount.text = getString(R.string.question_count, questions.size)
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            // Observe UI state
            quizViewModel.uiState.collectLatest { uiState ->
                binding.progressBar.isVisible = uiState is QuizViewModel.UiState.Loading
                
                if (uiState is QuizViewModel.UiState.Error) {
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
