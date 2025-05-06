package com.example.kotlinquiz.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.kotlinquiz.R

class QuizProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progressPercentage: Float = 0f
    private var completedColor: Int = ContextCompat.getColor(context, R.color.purple_500)
    private var remainingColor: Int = ContextCompat.getColor(context, R.color.purple_200)
    private var progressBarHeight: Float = context.resources.getDimension(R.dimen.default_progress_height)
    private var animationDuration: Int = DEFAULT_ANIMATION_DURATION

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var progressAnimator: ValueAnimator? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.QuizProgressView
            )

            try {
                progressPercentage = typedArray.getFloat(
                    R.styleable.QuizProgressView_progressPercentage,
                    0f
                )
                completedColor = typedArray.getColor(
                    R.styleable.QuizProgressView_completedColor,
                    completedColor
                )
                remainingColor = typedArray.getColor(
                    R.styleable.QuizProgressView_remainingColor,
                    remainingColor
                )
                progressBarHeight = typedArray.getDimension(
                    R.styleable.QuizProgressView_progressBarHeight,
                    progressBarHeight
                )
                animationDuration = typedArray.getInteger(
                    R.styleable.QuizProgressView_animationDuration,
                    DEFAULT_ANIMATION_DURATION
                )
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw remaining progress background
        paint.color = remainingColor
        canvas.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            paint
        )

        // Draw completed progress
        paint.color = completedColor
        canvas.drawRect(
            0f,
            0f,
            width * (progressPercentage / 100f),
            height.toFloat(),
            paint
        )
    }

    fun setProgress(progress: Float, animate: Boolean = true) {
        progressAnimator?.cancel()

        if (animate) {
            progressAnimator = ValueAnimator.ofFloat(progressPercentage, progress).apply {
                duration = animationDuration.toLong()
                addUpdateListener { animator ->
                    progressPercentage = animator.animatedValue as Float
                    invalidate()
                }
                start()
            }
        } else {
            progressPercentage = progress
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = progressBarHeight.toInt()
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            height
        )
    }

    companion object {
        private const val DEFAULT_ANIMATION_DURATION = 300
    }
}
