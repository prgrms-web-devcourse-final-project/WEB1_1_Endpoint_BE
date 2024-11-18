package com.grepp.quizy.quiz.api.quiz

import QuizResponse
import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.quiz.dto.CreateQuizRequest
import com.grepp.quizy.quiz.api.quiz.dto.UpdateQuizRequest
import com.grepp.quizy.quiz.domain.quiz.*
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz")
class QuizApi(
        private val quizCreateUseCase: QuizCreateUseCase,
        private val quizUpdateUseCase: QuizUpdateUseCase,
        private val quizDeleteUseCase: QuizDeleteUseCase,
) {

    @PostMapping
    fun createQuiz(
            @RequestBody request: CreateQuizRequest
    ): ApiResponse<QuizResponse> =
            ApiResponse.success(
                    QuizResponse.from(
                            quizCreateUseCase.create(
                                    request.type,
                                    request.toContent(),
                                    request.toAnswer(),
                            )
                    )
            )

    @PutMapping("/{id}")
    fun updateQuiz(
            @PathVariable id: Long,
            @RequestBody request: UpdateQuizRequest,
    ): ApiResponse<QuizResponse> =
            ApiResponse.success(
                    QuizResponse.from(
                            quizUpdateUseCase.update(
                                    QuizId(id),
                                    request.toContent(),
                                    request.toAnswer(),
                            )
                    )
            )

    @DeleteMapping("/{id}")
    fun deleteQuiz(
            @PathVariable id: Long
    ): ApiResponse<Unit> {
        quizDeleteUseCase.delete(QuizId(id))
        return ApiResponse.success("퀴즈가 삭제되었습니다.")
    }
}
