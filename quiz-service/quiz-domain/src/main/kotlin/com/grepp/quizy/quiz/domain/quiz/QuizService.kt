package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Service

@Service
class QuizService(
        private val quizReader: QuizReader,
        private val quizAppender: QuizAppender,
        private val quizUpdater: QuizUpdater,
        private val quizValidator: QuizValidator,
        private val quizRemover: QuizRemover,
        private val quizTagManager: QuizTagManager,
) :
        QuizCreateUseCase,
        QuizUpdateUseCase,
        QuizDeleteUseCase,
        QuizReadUseCase {

    override fun create(
            creatorId: UserId,
            type: QuizType,
            content: QuizContent,
            answer: QuizAnswer,
    ): Quiz {
        val preparedContent = quizTagManager.saveNewTags(content)
        return quizAppender.append(
                creatorId,
                type,
                preparedContent,
                answer,
        )
    }

    // TODO: Pagination
    override fun getQuizTags(ids: List<QuizTagId>): List<QuizTag> {
        return quizReader.readTags(ids)
    }

    override fun update(
            id: QuizId,
            updatorId: UserId,
            updatedContent: QuizContent,
            updatedAnswer: QuizAnswer?,
    ): Quiz {
        val quiz = quizReader.read(id)
        val preparedContent =
                quizTagManager.saveNewTags(updatedContent)
        quizValidator.validateUpdatable(quiz)
        return quizUpdater.update(
                quiz,
                updatorId,
                preparedContent,
                updatedAnswer,
        )
    }

    override fun delete(id: QuizId, deleterId: UserId) {
        val quiz = quizReader.read(id)
        quizRemover.remove(quiz, deleterId)
    }
}
