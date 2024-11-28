package com.grepp.quizy.quiz.api.quiz

import QuizResponse
import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.quiz.dto.CreateQuizRequest
import com.grepp.quizy.quiz.api.quiz.dto.UpdateQuizRequest
import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
        private val quizReadUseCase: QuizReadUseCase,
) {

    @PostMapping
    fun createQuiz(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody request: CreateQuizRequest,
    ): ApiResponse<QuizResponse> =
            ApiResponse.success(
                    QuizResponse.from(
                            quizCreateUseCase.create(
                                    UserId(userPrincipal.value),
                                    request.type,
                                    request.toContent(),
                                    request.toAnswer(),
                            )
                    )
            )

    @GetMapping("/tags")
    fun getQuizTag(
            @RequestBody ids: List<Long>
    ): ApiResponse<List<QuizTag>> =
            ApiResponse.success(
                    quizReadUseCase.getQuizTags(
                            ids.map { QuizTagId(it) }
                    )
            )

    //TODO: 퀴즈 이미지 수정 반영
    @PutMapping("/{id}")
    fun updateQuiz(
            @PathVariable id: Long,
            @AuthUser userPrincipal: UserPrincipal,
            @RequestBody request: UpdateQuizRequest,
    ): ApiResponse<QuizResponse> =
            ApiResponse.success(
                    QuizResponse.from(
                            quizUpdateUseCase.update(
                                    QuizId(id),
                                    UserId(userPrincipal.value),
                                    request.toContent(),
                                    request.toAnswer(),
                                    request.deleteImageIds.map { QuizImageId.from(it) }
                            )
                    )
            )

    @DeleteMapping("/{id}")
    fun deleteQuiz(
            @PathVariable id: Long,
            userId: Long,
    ): ApiResponse<Unit> {
        quizDeleteUseCase.delete(QuizId(id), UserId(userId))
        return ApiResponse.success("퀴즈가 삭제되었습니다.")
    }
}
