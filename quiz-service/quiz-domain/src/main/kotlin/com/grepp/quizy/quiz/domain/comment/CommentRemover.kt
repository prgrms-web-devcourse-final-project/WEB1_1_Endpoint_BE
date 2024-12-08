package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizReader
import com.grepp.quizy.quiz.domain.quiz.QuizUpdater
import org.springframework.stereotype.Component

@Component
class CommentRemover(
    private val commentRepository: CommentRepository,
    private val quizReader: QuizReader,
    private val quizUpdater: QuizUpdater,
) {

    fun remove(comment: Comment) {
        val quiz = quizReader.readWithLock(comment.quizId)
        quizUpdater.decreaseCommentCount(quiz)
        commentRepository.delete(comment)
    }
}
