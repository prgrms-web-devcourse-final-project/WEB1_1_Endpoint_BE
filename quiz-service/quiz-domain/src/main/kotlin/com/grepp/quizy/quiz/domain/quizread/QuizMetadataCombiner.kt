package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.like.LikeManager
import com.grepp.quizy.quiz.domain.like.QuizLikePackage
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.quiz.QuizReader
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerPackage
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerReader
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizMetadataCombiner(
    private val quizReader: QuizReader,
    private val likeManager: LikeManager,
    private val userAnswerReader: UserAnswerReader,
) {

    fun combine(
        userId: UserId?,
        searchedQuizzes: Slice<Quiz>,
    ): List<QuizWithDetail> {
        val quizIds = parseQuizIds(searchedQuizzes.content)
        val counts = quizReader.readCounts(quizIds)

        val likeStatus = userId?.let { id ->
            likeManager.isLikedIn(id, quizIds)
        } ?: QuizLikePackage()
        val userAnswer = userId?.let { id ->
            userAnswerReader.read(id, quizIds)
        } ?: UserAnswerPackage()

        return searchedQuizzes.content.map { quiz ->
            QuizDTOFactory.QuizWithDetail(
                    quiz,
                    counts.getCountOf(quiz.id),
                    likeStatus.isLikedBy(quiz.id),
                    userAnswer.getChoiceOf(quiz.id),
            )
        }
    }

    fun combineWithoutUserAnswer(userId: UserId, searchedQuizzes: Slice<Quiz>): List<QuizWithDetail> {
        val quizIds = parseQuizIds(searchedQuizzes.content)
        val counts = quizReader.readCounts(quizIds)

        val likeStatus = likeManager.isLikedIn(userId, quizIds)

        return searchedQuizzes.content.map { quiz ->
            QuizDTOFactory.QuizWithDetail(
                quiz,
                counts.getCountOf(QuizId(quiz.id.value)),
                likeStatus.isLikedBy(QuizId(quiz.id.value)),
                null,
            )
        }
    }

    private fun parseQuizIds(quizzes: List<Quiz>) =
            quizzes.map { QuizId(it.id.value) }
}
