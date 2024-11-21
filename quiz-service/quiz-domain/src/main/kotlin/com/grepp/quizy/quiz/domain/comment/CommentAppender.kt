package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.quiz.QuizReader
import com.grepp.quizy.quiz.domain.quiz.QuizUpdater
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Component

@Component
class CommentAppender(
        private val commentRepository: CommentRepository,
        private val quizReader: QuizReader,
        private val quizUpdater: QuizUpdater,
) {
    fun append(
            quizId: QuizId,
            writerId: UserId,
            parentCommentId: CommentId,
            content: CommentContent,
    ): Comment {
        val comment =
                Comment(
                        quizId = quizId,
                        writerId = writerId,
                        parentCommentId = parentCommentId,
                        _content = content,
                )
        val quiz = quizReader.readWithLock(quizId)
        quiz.increaseCommentCount()
        quizUpdater.update(quiz)
        return commentRepository.save(comment)
    }
}
