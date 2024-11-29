package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.image.QuizImageManager
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val quizReader: QuizReader,
    private val quizAppender: QuizAppender,
    private val quizUpdater: QuizUpdater,
    private val quizValidator: QuizValidator,
    private val quizRemover: QuizRemover,
    private val quizTagManager: QuizTagManager,
    private val quizImageRemover: QuizImageManager,

    ) : QuizCreateUseCase,
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

    override fun getQuizTags(ids: List<QuizTagId>): List<QuizTag> {
        return quizReader.readTags(ids)
    }

    override fun update(
        id: QuizId,
        updatorId: UserId,
        updatedContent: QuizContent,
        updatedAnswer: QuizAnswer?,
        deleteImageIds: List<QuizImageId>,
    ): Quiz {
        val quiz = quizReader.readWithLock(id)
        val preparedContent = quizTagManager.saveNewTags(updatedContent)
        quizValidator.validateUpdatable(quiz)
        quizImageRemover.remove(deleteImageIds)
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
