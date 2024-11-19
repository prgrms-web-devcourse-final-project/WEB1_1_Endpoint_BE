package com.grepp.quizy.search.infra.quiz.repository

import co.elastic.clients.elasticsearch.security.User
import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.quiz.QuizSearchRepository
import com.grepp.quizy.search.domain.global.dto.SearchCondition
import com.grepp.quizy.search.domain.quiz.Quiz
import com.grepp.quizy.search.domain.quiz.QuizId
import com.grepp.quizy.search.domain.quiz.UserAnswer
import com.grepp.quizy.search.infra.quiz.document.QuizDomainFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class QuizSearchRepositoryAdapter(
    private val elasticRepository: QuizElasticRepository
) : QuizSearchRepository {

    override fun search(condition: SearchCondition): Slice<Quiz> {
        val pageable = convertPageable(condition)
        return elasticRepository.search(condition.field, pageable).let { slice ->
            Slice(slice.content.map { QuizDomainFactory.toQuiz(it) }, slice.hasNext())
        }
    }

    override fun searchUserAnswer(quizIds: List<QuizId>): UserAnswer =
        UserAnswer(mapOf())

    // @TODO 리팩토링 필요
    //  SearchCondition 쪽에서 해야 될 거 같은데 PageRequest 사용 불가..
    private fun convertPageable(condition: SearchCondition) =
        PageRequest.of(
            condition.page(),
            condition.size(),
            Sort.by(Sort.Direction.DESC, condition.sort),
        )
}