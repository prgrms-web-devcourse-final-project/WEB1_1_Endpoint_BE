package com.grepp.quizy.quiz.api.comment

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.comment.dto.CommentResponse
import com.grepp.quizy.quiz.api.comment.dto.CreateCommentRequest
import com.grepp.quizy.quiz.domain.comment.*
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quiz/comments")
class CommentApi(
        private val commentCreateUseCase: CommentCreateUseCase,
        private val commentReadUseCase: CommentReadUseCase,
        private val commentUpdateUseCase: CommentUpdateUseCase,
        private val commentDeleteUseCase: CommentDeleteUseCase,
) {

    @PostMapping
    fun createComment(
            @RequestBody request: CreateCommentRequest
    ): ApiResponse<CommentId> =
            ApiResponse.success(
                    commentCreateUseCase
                            .createComment(
                                    request.toQuizId(),
                                    request.toWriterId(),
                                    request.toParentCommentId(),
                                    request.toContent(),
                            )
                            .id
            )

    @GetMapping
    fun getComments(
            quizId: Long
    ): ApiResponse<List<CommentResponse>> =
            ApiResponse.success(
                    commentReadUseCase
                            .getComments(QuizId(quizId))
                            .map { CommentResponse.from(it) }
            )

    @PutMapping("/{id}")
    fun updateComment(
            @PathVariable id: Long,
            userId: Long,
            updateContent: String,
    ): ApiResponse<Comment> =
            ApiResponse.success(
                    commentUpdateUseCase.updateComment(
                            CommentId(id),
                            UserId(userId),
                            CommentContent(updateContent),
                    )
            )

    @DeleteMapping("/{id}")
    fun deleteComment(
            @PathVariable id: Long,
            userId: Long,
    ): ApiResponse<Unit> =
            ApiResponse.success(
                    commentDeleteUseCase.deleteComment(
                            CommentId(id),
                            UserId(userId),
                    )
            )
}
