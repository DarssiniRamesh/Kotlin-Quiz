package com.example.kotlinquiz.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Data class representing an Answer entity
 * Records user answers to questions within a quiz attempt
 */
@Entity(
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = QuizAttemptEntity::class,
            parentColumns = ["id"],
            childColumns = ["quizAttemptId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("questionId"),
        Index("quizAttemptId"),
        Index("questionId", "quizAttemptId", unique = true)
    ]
)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val questionId: Int,
    val quizAttemptId: Int,
    val selectedOptionIndex: Int?,
    val isCorrect: Boolean = false,
    val timeSpentInSeconds: Int = 0
)
