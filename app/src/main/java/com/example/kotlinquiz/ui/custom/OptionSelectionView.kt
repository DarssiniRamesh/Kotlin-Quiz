package com.example.kotlinquiz.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.kotlinquiz.R

class OptionSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var optionText: String = ""
    private var normalBackgroundColor: Int = ContextCompat.getColor(context, R.color.white)
    private var selectedBackgroundColor: Int = ContextCompat.getColor(context, R.color.purple_200)
    private var correctBackgroundColor: Int = ContextCompat.getColor(context, R.color.teal_200)
    private var incorrectBackgroundColor: Int = ContextCompat.getColor(context, android.R.color.holo_red_light)
    private var optionTextColor: Int = ContextCompat.getColor(context, R.color.black)
    private var optionTextSize: Float = context.resources.getDimension(R.dimen.default_text_size)
    private var optionPadding: Float = context.resources.getDimension(R.dimen.default_padding)
    private var cornerRadius: Float = context.resources.getDimension(R.dimen.default_corner_radius)

    private var currentState: OptionState = OptionState.NORMAL
    private var isSelected: Boolean = false
    private var onOptionSelectedListener: ((Boolean) -> Unit)? = null

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    private val rect = RectF()

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.OptionSelectionView
            )

            try {
                optionText = typedArray.getString(R.styleable.OptionSelectionView_optionText) ?: ""
                normalBackgroundColor = typedArray.getColor(
                    R.styleable.OptionSelectionView_normalBackgroundColor,
                    normalBackgroundColor
                )
                selectedBackgroundColor = typedArray.getColor(
                    R.styleable.OptionSelectionView_selectedBackgroundColor,
                    selectedBackgroundColor
                )
                correctBackgroundColor = typedArray.getColor(
                    R.styleable.OptionSelectionView_correctBackgroundColor,
                    correctBackgroundColor
                )
                incorrectBackgroundColor = typedArray.getColor(
                    R.styleable.OptionSelectionView_incorrectBackgroundColor,
                    incorrectBackgroundColor
                )
                optionTextColor = typedArray.getColor(
                    R.styleable.OptionSelectionView_optionTextColor,
                    optionTextColor
                )
                optionTextSize = typedArray.getDimension(
                    R.styleable.OptionSelectionView_optionTextSize,
                    optionTextSize
                )
                optionPadding = typedArray.getDimension(
                    R.styleable.OptionSelectionView_optionPadding,
                    optionPadding
                )
                cornerRadius = typedArray.getDimension(
                    R.styleable.OptionSelectionView_cornerRadius,
                    cornerRadius
                )
            } finally {
                typedArray.recycle()
            }
        }

        textPaint.apply {
            textSize = optionTextSize
            color = optionTextColor
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        backgroundPaint.color = when (currentState) {
            OptionState.NORMAL -> normalBackgroundColor
            OptionState.SELECTED -> selectedBackgroundColor
            OptionState.CORRECT -> correctBackgroundColor
            OptionState.INCORRECT -> incorrectBackgroundColor
        }

        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, backgroundPaint)

        // Draw text
        val xPos = width / 2f
        val yPos = (height / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f)
        canvas.drawText(optionText, xPos, yPos, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (currentState == OptionState.NORMAL || currentState == OptionState.SELECTED) {
                    isSelected = !isSelected
                    currentState = if (isSelected) OptionState.SELECTED else OptionState.NORMAL
                    onOptionSelectedListener?.invoke(isSelected)
                    invalidate()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun setText(text: String) {
        optionText = text
        invalidate()
    }

    fun setOptionState(state: OptionState) {
        currentState = state
        invalidate()
    }

    fun setOnOptionSelectedListener(listener: (Boolean) -> Unit) {
        onOptionSelectedListener = listener
    }

    enum class OptionState {
        NORMAL,
        SELECTED,
        CORRECT,
        INCORRECT
    }
}
