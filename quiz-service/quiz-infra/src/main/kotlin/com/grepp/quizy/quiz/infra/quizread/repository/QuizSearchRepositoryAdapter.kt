package com.grepp.quizy.quiz.infra.quizread.repository

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.quizread.*
import com.grepp.quizy.quiz.infra.quizread.document.QuizDomainFactory
import com.grepp.quizy.quiz.infra.quizread.document.SortField
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class QuizSearchRepositoryAdapter(
        private val quizElasticRepository: QuizElasticRepository,
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

    override fun search(condition: GameQuizSearchCondition): List<Quiz> {
        val pageable = convertPageable(condition)

        return quizElasticRepository
            .searchAnswerableQuiz(condition.category, condition.difficulty, pageable)
            .map { QuizDomainFactory.toAnswerableQuiz(it) }
    }

    override fun searchNotIn(answeredQuizIds: List<QuizId>, condition: UserSearchCondition): Slice<Quiz> {
        val pageable = convertPageable(condition)

        return quizElasticRepository.searchNotIn(condition.field, pageable, answeredQuizIds.map { it.value })
            .let { slice ->
                Slice(
                    slice.content.map {
                        QuizDomainFactory.toQuiz(it)
                    },
                    slice.hasNext(),
                )
            }
    }

    private fun convertPageable(condition: SearchCondition) =
        condition.sort()?.let {
            PageRequest.of(
                condition.page(),
                condition.size(),
                Sort.by(Sort.Direction.DESC, SortField.from(it).fieldName)
            )
        } ?: PageRequest.of(condition.page(), condition.size())
}
