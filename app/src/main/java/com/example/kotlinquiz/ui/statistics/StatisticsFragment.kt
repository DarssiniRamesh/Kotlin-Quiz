package com.example.kotlinquiz.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinquiz.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is StatisticsUiState.Loading -> showLoading()
                        is StatisticsUiState.Success -> showStatistics(state.statistics)
                        is StatisticsUiState.Error -> showError(state.message)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            loadingProgressBar.isVisible = true
            errorTextView.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding.apply {
            loadingProgressBar.isVisible = false
            errorTextView.isVisible = true
            errorTextView.text = message
        }
    }

    private fun showStatistics(statistics: Statistics) {
        binding.apply {
            loadingProgressBar.isVisible = false
            errorTextView.isVisible = false

            // Update overview statistics
            totalQuizzesTaken.text = statistics.totalQuizzesTaken.toString()
            averageScore.text = String.format("%.1f%%", statistics.averageScore)
            totalTime.text = formatDuration(statistics.totalTimeTaken)
        }
    }

    private fun formatDuration(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            minutes > 0 -> "${minutes}m"
            else -> "< 1m"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
