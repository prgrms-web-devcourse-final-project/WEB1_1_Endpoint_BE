package com.grepp.quizy.quiz.api.useranswer

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.quiz.api.useranswer.dto.QuizWithUserAnswerResponse
import com.grepp.quizy.quiz.api.useranswer.dto.UserAnswerRequest
import com.grepp.quizy.quiz.api.useranswer.dto.UserAnswerReviewRequest
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerCreateUseCase
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerKey
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerReadService
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerUpdateUseCase
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.annotation.CursorDefault
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/user-answers")
class UserAnswerApi(
        private val userAnswerCreateUseCase: UserAnswerCreateUseCase,
        private val userAnswerReadService: UserAnswerReadService,
        private val userAnswerUpdateUseCase: UserAnswerUpdateUseCase
) {

    @PostMapping
    fun create(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody request: UserAnswerRequest
    ): ApiResponse<Unit> =
            userAnswerCreateUseCase
                    .createUserAnswer(request.toId(userPrincipal.value), request.choiceNumber)
                    .let { ApiResponse.success() }

    @GetMapping("/incorrect")
    fun getIncorrectQuizzes(
        @AuthUser userPrincipal: UserPrincipal,
        @CursorDefault cursor: Cursor
    ): ApiResponse<SliceResult<QuizWithUserAnswerResponse>> =
            ApiResponse.success(
                    userAnswerReadService
                            .getIncorrectQuizzes(UserId(userPrincipal.value), cursor)
                            .map { QuizWithUserAnswerResponse.from(it) }
            )

    @PatchMapping
    fun reviewUserAnswer(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody request: UserAnswerReviewRequest
    ): ApiResponse<Unit> =
            userAnswerUpdateUseCase
                    .reviewUserAnswer(request.toUserAnswerKey(userPrincipal.value), request.reviewStatus)
                    .let { ApiResponse.success() }
}
