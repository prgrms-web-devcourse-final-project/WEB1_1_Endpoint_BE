package com.grepp.quizy.quiz.api.useranswer

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.useranswer.dto.UserAnswerRequest
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerCreateUseCase
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
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
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody request: UserAnswerRequest
    ): ApiResponse<Unit> =
            userAnswerCreateUseCase
                    .createUserAnswer(request.toId(userPrincipal.value), request.choiceNumber)
                    .let { ApiResponse.success() }
}
