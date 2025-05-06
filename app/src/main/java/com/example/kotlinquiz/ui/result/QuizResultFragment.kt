package com.example.kotlinquiz.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinquiz.databinding.FragmentQuizResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class QuizResultFragment : Fragment() {
    private var _binding: FragmentQuizResultBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: QuizResultViewModel by viewModels()
    private val args: QuizResultFragmentArgs by navArgs()
    private lateinit var answerAdapter: QuizResultAnswerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        loadQuizResults()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        answerAdapter = QuizResultAnswerAdapter()
        binding.recyclerAnswers.apply {
            adapter = answerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupClickListeners() {
        binding.buttonFinish.setOnClickListener {
            findNavController().navigate(
                QuizResultFragmentDirections.actionQuizResultFragmentToQuizListFragment()
            )
        }
    }

    private fun loadQuizResults() {
        viewModel.loadQuizResults(args.quizAttemptId)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.quizResultState.collectLatest { state ->
                when (state) {
                    is QuizResultState.Success -> {
                        displayResults(state.quizResult)
                    }
                    is QuizResultState.Error -> {
                        // Handle error state
                    }
                    QuizResultState.Loading -> {
                        // Show loading state
                    }
                }
            }
        }
    }

    private fun displayResults(result: QuizResult) {
        binding.apply {
            textScore.text = "Score: ${result.score}%"
            
            val duration = result.timeTaken
            val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60
            textTimeTaken.text = "Time taken: ${String.format("%02d:%02d", minutes, seconds)}"
            
            textTotalQuestions.text = result.totalQuestions.toString()
            textCorrectAnswers.text = result.correctAnswers.toString()
            textIncorrectAnswers.text = result.incorrectAnswers.toString()
            
            answerAdapter.submitList(result.answers)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
