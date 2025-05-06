package com.example.kotlinquiz.ui.question

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquiz.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Adapter for handling question options in RecyclerView
 */
class QuestionOptionAdapter : RecyclerView.Adapter<QuestionOptionAdapter.OptionViewHolder>() {
    
    private val options = mutableListOf<String>()
    
    /**
     * Updates the list of options and notifies the adapter
     */
    fun updateOptions(newOptions: List<String>) {
        options.clear()
        options.addAll(newOptions)
        notifyDataSetChanged()
    }
    
    /**
     * Returns the current list of options
     */
    fun getOptions(): List<String> = options.toList()
    
    /**
     * Adds a new empty option and notifies the adapter
     */
    fun addOption() {
        options.add("")
        notifyItemInserted(options.size - 1)
    }
    
    /**
     * Removes the last option and notifies the adapter
     */
    fun removeLastOption() {
        if (options.isNotEmpty()) {
            options.removeAt(options.size - 1)
            notifyItemRemoved(options.size)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question_option, parent, false)
        return OptionViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position], position)
    }
    
    override fun getItemCount(): Int = options.size
    
    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textInputLayout: TextInputLayout = 
            itemView.findViewById(R.id.text_input_layout_option)
        private val editText: TextInputEditText = 
            itemView.findViewById(R.id.edit_text_option)
        
        fun bind(option: String, position: Int) {
            // Update hint with position number (1-based index)
            textInputLayout.hint = itemView.context.getString(R.string.option_hint, position + 1)
            
            // Set the option text
            editText.setText(option)
            
            // Add text change listener
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                
                override fun afterTextChanged(s: Editable?) {
                    options[bindingAdapterPosition] = s.toString()
                }
            })
        }
    }
}
