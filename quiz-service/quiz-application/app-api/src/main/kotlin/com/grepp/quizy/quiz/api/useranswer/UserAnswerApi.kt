package com.grepp.quizy.quiz.api.useranswer

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.useranswer.dto.UserAnswerRequest
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerCreateUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/user-answers")
class UserAnswerApi(
        private val userAnswerCreateUseCase: UserAnswerCreateUseCase
) {

    @PostMapping
    fun create(
            @RequestBody request: UserAnswerRequest
    ): ApiResponse<Unit> =
            userAnswerCreateUseCase
                    .create(request.toId(), request.choice)
                    .let { ApiResponse.success() }
}