package com.grepp.quizy.quiz.domain.quiz

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
        QuizDeleteUseCase {

    override fun create(
            type: QuizType,
            content: QuizContent,
            answer: QuizAnswer,
    ): Quiz {
        val preparedContent =
                quizTagManager.saveNewTags(content)
        return quizAppender.append(
                type,
                preparedContent,
                answer,
        )
    }

    override fun update(
            id: QuizId,
            updatedContent: QuizContent,
            updatedAnswer: QuizAnswer?,
    ): Quiz {
        val quiz = quizReader.read(id)
        val preparedContent =
                quizTagManager.saveNewTags(updatedContent)
        //        quizValidator.validateUpdatable(quiz)
        return quizUpdater.update(
                quiz,
                preparedContent,
                updatedAnswer,
        )
    }

    override fun delete(id: QuizId) {
        val quiz = quizReader.read(id)
        quizRemover.remove(quiz)
    }
}
