package com.grepp.quizy.quiz.infra.quizread.repository

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.quizread.*
import com.grepp.quizy.quiz.infra.log.repository.LogElasticRepository
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument
import com.grepp.quizy.quiz.infra.quizread.document.QuizDomainFactory
import com.grepp.quizy.quiz.infra.quizread.document.SortField
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class QuizSearchRepositoryAdapter(
        private val quizElasticRepository: QuizElasticRepository,
    private val logElasticRepository: LogElasticRepository
) : QuizSearchRepository {

    override fun save(quiz: Quiz) {
        quizElasticRepository.save(QuizDocument.from(quiz))
    }

    override fun saveAll(quizList: List<Quiz>) {
        val map = quizList.map { QuizDocument.from(it) }
        quizElasticRepository.saveAll(map)
    }

    override fun findById(quizId: QuizId): Quiz? {
        return quizElasticRepository.findByIdOrNull(quizId.value)?.let {
            QuizDomainFactory.toQuiz(it)
        }
    }

    override fun search(condition: UserSearchCondition): Slice<Quiz> {
        val pageable = convertPageable(condition)

        return quizElasticRepository
                .search(condition.fields, pageable)
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

        return quizElasticRepository.searchNotIn(condition.fields, pageable, answeredQuizIds.map { it.value })
            .let { slice ->
                Slice(
                    slice.content.map {
                        QuizDomainFactory.toQuiz(it)
                    },
                    slice.hasNext(),
                )
            }
    }

    override fun searchNotIn(answeredQuizIds: List<QuizId>, condition: FeedSearchCondition): Slice<Quiz> {
        val pageable = convertPageable(condition)

        return quizElasticRepository.searchByCategoryNotIn(condition.interest!!.name, pageable, answeredQuizIds.map { it.value })
            .let { slice ->
                Slice(
                    slice.content.map {
                        QuizDomainFactory.toQuiz(it)
                    },
                    slice.hasNext(),
                )
            }
    }

    override fun searchNotIn(condition: FeedSearchCondition): Slice<Quiz> {
        val pageable = convertPageable(condition)

        return quizElasticRepository.searchByCategory(condition.interest!!.name, pageable)
            .let { slice ->
                Slice(
                    slice.content.map {
                        QuizDomainFactory.toQuiz(it)
                    },
                    slice.hasNext(),
                )
            }
    }

    override fun searchTrendingKeyword(): List<String> {
        return logElasticRepository.topKKeyword(10)
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
