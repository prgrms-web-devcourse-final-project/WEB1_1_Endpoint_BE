package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.QuizCategory

data class QuizFeed(val category: QuizCategory, val slice: Slice<QuizWithDetail>)
