package com.grepp.quizy.search.infra.quiz.repository

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.quiz.*
import com.grepp.quizy.search.domain.quiz.UserSearchCondition
import com.grepp.quizy.search.domain.user.UserId
import com.grepp.quizy.search.infra.quiz.document.QuizDomainFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class QuizSearchRepositoryAdapter(
        private val quizElasticRepository: QuizElasticRepository,
        private val userAnswerElasticRepository:
                UserAnswerElasticRepository,
) : QuizSearchRepository {

    override fun search(condition: UserSearchCondition): Slice<Quiz> {
        val pageable = convertPageable(condition)

        return quizElasticRepository
                .search(condition.field, pageable)
                .let { slice ->
                    Slice(
                            slice.content.map {
                                QuizDomainFactory.toQuiz(it)
                            },
                            slice.hasNext(),
                    )
                }
    }

    override fun search(condition: GameSearchCondition): List<AnswerableQuiz> {
        val pageable = convertPageable(condition)

        return quizElasticRepository
            .searchAnswerableQuiz(condition.category, condition.difficulty.criteria, pageable)
            .map { QuizDomainFactory.toAnswerableQuiz(it) }
    }

    override fun searchUserAnswer(
            userId: UserId,
            quizIds: List<QuizId>,
    ): UserAnswer {
        val userAnswer =
                userAnswerElasticRepository.findByIdOrNull(userId.id)
        return userAnswer?.let { answer ->
            UserAnswer(
                    quizIds.mapNotNull { id ->
                                answer.getOptionNumber(id)
                            }
                            .toMap()
            )
        } ?: UserAnswer()
    }

    private fun convertPageable(condition: SearchCondition) =
        condition.sort()?.let {
            PageRequest.of(
                condition.page(),
                condition.size(),
                Sort.by(Sort.Direction.DESC, it.name)
            )
        } ?: PageRequest.of(condition.page(), condition.size())
}
