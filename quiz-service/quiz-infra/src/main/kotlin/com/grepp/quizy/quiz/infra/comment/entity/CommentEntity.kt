package com.grepp.quizy.quiz.infra.comment.entity

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.jpa.BaseTimeEntity
import com.grepp.quizy.quiz.domain.comment.Comment
import com.grepp.quizy.quiz.domain.comment.CommentContent
import com.grepp.quizy.quiz.domain.comment.CommentId
import com.grepp.quizy.quiz.domain.comment.Writer
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.infra.user.entity.QuizUserEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
class CommentEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val quizId: Long,
        val writerId: Long,
        val parentCommentId: Long,
        val content: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
) : BaseTimeEntity(createdAt, updatedAt) {
    fun toDomain(writer: QuizUserEntity) =
            Comment(
                    id = CommentId(id),
                    quizId = QuizId(quizId),
                    writer = Writer(
                            id = UserId(writerId),
                            name = writer.name,
                            profileImageUrl = writer.imgPath,
                    ),
                    parentCommentId = CommentId(parentCommentId),
                    _content = CommentContent(content),
                    dateTime = DateTime(createdAt, updatedAt),
            )

    companion object {
        fun from(comment: Comment) =
                CommentEntity(
                                id = comment.id.value,
                                quizId = comment.quizId.value,
                                writerId = comment.writer.id.value,
                                parentCommentId = comment.parentCommentId.value,
                                content = comment.content.value,
                                createdAt = comment.dateTime.createdAt,
                                updatedAt = comment.dateTime.updatedAt,
                        )
    }
}
