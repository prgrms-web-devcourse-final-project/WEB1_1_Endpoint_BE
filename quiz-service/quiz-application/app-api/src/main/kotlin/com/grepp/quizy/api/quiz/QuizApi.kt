package com.grepp.quizy.api.quiz

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.domain.quiz.Quiz
import com.grepp.quizy.domain.quiz.QuizService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz")
class QuizApi(private val quizService: QuizService) {

    @PostMapping
    fun createQuiz(title: String): ApiResponse<Quiz> {
        return ApiResponse.success(quizService.createQuiz(title))
    }
}
