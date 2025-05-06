package com.example.kotlinquiz.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.kotlinquiz.data.preferences.UserPreferences
import com.example.kotlinquiz.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment for managing user preferences and settings
 */
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPreferences()
        setupListeners()
    }

    private fun loadPreferences() {
        // Load theme preference
        binding.switchTheme.isChecked = userPreferences.isDarkTheme

        // Load notifications preference
        binding.switchNotifications.isChecked = userPreferences.notificationsEnabled

        // Load default difficulty
        val difficultyRadioButton = when (userPreferences.defaultDifficulty) {
            0 -> binding.radioDifficultyEasy
            1 -> binding.radioDifficultyMedium
            2 -> binding.radioDifficultyHard
            else -> binding.radioDifficultyEasy
        }
        difficultyRadioButton.isChecked = true

        // Load default time limit
        binding.editTextTimeLimit.setText(userPreferences.defaultTimeLimit.toString())
    }

    private fun setupListeners() {
        // Theme switch listener
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            userPreferences.isDarkTheme = isChecked
            updateTheme(isChecked)
            showSettingsSaved()
        }

        // Notifications switch listener
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            userPreferences.notificationsEnabled = isChecked
            showSettingsSaved()
        }

        // Difficulty radio group listener
        binding.radioGroupDifficulty.setOnCheckedChangeListener { group, checkedId ->
            val difficulty = when (group.findViewById<RadioButton>(checkedId)) {
                binding.radioDifficultyEasy -> 0
                binding.radioDifficultyMedium -> 1
                binding.radioDifficultyHard -> 2
                else -> 0
            }
            userPreferences.defaultDifficulty = difficulty
            showSettingsSaved()
        }

        // Time limit input listener
        binding.editTextTimeLimit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val timeLimit = binding.editTextTimeLimit.text.toString().toIntOrNull() ?: 30
                userPreferences.defaultTimeLimit = timeLimit
                showSettingsSaved()
            }
        }
    }

    private fun updateTheme(isDarkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun showSettingsSaved() {
        Snackbar.make(binding.root, getString(R.string.settings_saved), Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
