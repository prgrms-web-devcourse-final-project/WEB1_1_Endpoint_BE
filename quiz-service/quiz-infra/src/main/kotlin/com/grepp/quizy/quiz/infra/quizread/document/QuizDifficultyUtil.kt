package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.domain.quiz.QuizType
import kotlin.math.ln

class QuizDifficultyUtil {
    companion object {
        fun calculateDifficulty(quiz: QuizDocument): QuizDifficulty {
            if (isUncalculatable(quiz)) return QuizDifficulty.NONE
            if (quiz.totalAnsweredUsers == 0L) return QuizDifficulty.MEDIUM

            val correctionRatio = quiz.calculateCorrectionRatio()
            val difficultyScore = correctionRatio * ln((quiz.totalAnsweredUsers + 1).toDouble())
            return QuizDifficulty.of(difficultyScore)
        }

        private fun isUncalculatable(quiz: QuizDocument) =
            when (quiz.type) {
                QuizType.AB_TEST -> true
                QuizType.OX -> quiz.options.size < 2
                QuizType.MULTIPLE_CHOICE -> quiz.options.size < 4
            }
    }
}