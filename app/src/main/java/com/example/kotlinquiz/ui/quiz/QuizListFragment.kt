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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinquiz.R
import com.example.kotlinquiz.data.model.Quiz
import com.example.kotlinquiz.databinding.FragmentQuizListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for displaying the list of available quizzes
 */
@AndroidEntryPoint
class QuizListFragment : Fragment() {

    private var _binding: FragmentQuizListBinding? = null
    private val binding get() = _binding!!

    private val quizViewModel: QuizViewModel by viewModels()
    private lateinit var quizAdapter: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        
        // Load quizzes when the fragment is created
        quizViewModel.loadQuizzes()
    }

    private fun setupRecyclerView() {
        quizAdapter = QuizAdapter { quiz ->
            // Navigate to quiz detail fragment
            findNavController().navigate(
                QuizListFragmentDirections.actionQuizListFragmentToQuizDetailFragment(quiz.id)
            )
        }
        
        binding.quizRecyclerView.apply {
            adapter = quizAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupClickListeners() {
        binding.fabAddQuiz.setOnClickListener {
            findNavController().navigate(
                QuizListFragmentDirections.actionQuizListFragmentToCreateQuizFragment()
            )
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Observe quiz list
            quizViewModel.quizListState.collectLatest { quizzes ->
                updateUI(quizzes)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            // Observe UI state
            quizViewModel.uiState.collectLatest { uiState ->
                when (uiState) {
                    is QuizViewModel.UiState.Error -> {
                        binding.quizRecyclerView.isVisible = true
                        Snackbar.make(
                            binding.root,
                            getString(R.string.error_loading_quizzes),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    QuizViewModel.UiState.Loading -> {
                        // Could show a loading indicator here
                    }
                    else -> {
                        // Success or Idle - UI is already updated by quizListState
                    }
                }
            }
        }
    }

    private fun updateUI(quizzes: List<Quiz>) {
        if (quizzes.isEmpty()) {
            binding.quizRecyclerView.isVisible = false
            binding.emptyView.isVisible = true
        } else {
            binding.quizRecyclerView.isVisible = true
            binding.emptyView.isVisible = false
            quizAdapter.submitList(quizzes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
