package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.grepp.quizy.quiz.domain.quiz.exception.QuizException
import com.grepp.quizy.quiz.infra.quiz.repository.QuizTagJpaRepository
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument
import com.grepp.quizy.quiz.infra.quizread.document.QuizOptionVO
import com.grepp.quizy.quiz.infra.quizread.repository.QuizElasticRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class QuizDocumentSynchronizer(
    private val quizElasticRepository: QuizElasticRepository,
    private val quizTagJpaRepository: QuizTagJpaRepository
) {

    fun createQuiz(quizCDCEvent: QuizCDCEvent) {
        val quizDoc = QuizDocument.from(quizCDCEvent)
        quizElasticRepository.save(quizDoc)

    }

    fun updateQuiz(updateQuiz: QuizCDCEvent) {
        val quizDoc = quizElasticRepository.findByIdOrNull(updateQuiz.id)
            ?: throw QuizException.NotFound
        quizDoc.update(updateQuiz)
        quizElasticRepository.save(quizDoc)
    }

    fun removeQuiz(id: Long) {
        quizElasticRepository.deleteById(id)
    }

    fun addQuizOption(quizOptionCDCEvent: QuizOptionCDCEvent) {
        quizElasticRepository.findById(quizOptionCDCEvent.quizId).ifPresent {
            it.addQuizOption(QuizOptionVO.from(quizOptionCDCEvent))
            quizElasticRepository.save(it)
        }
    }

    fun removeQuizOption(quizId: Long, number: Int) {
        quizElasticRepository.findById(quizId).ifPresent {
            it.removeQuizOption(number)
            quizElasticRepository.save(it)
        }
    }

    fun addQuizTag(quizTagMappingCDCEvent: QuizTagMappingCDCEvent) {
        quizElasticRepository.findById(quizTagMappingCDCEvent.quizId).ifPresent {
            quizTagJpaRepository.findById(quizTagMappingCDCEvent.tagId).ifPresent { tag ->
                it.addQuizTag(tag.name)
                quizElasticRepository.save(it)
            }
        }
    }

    fun removeQuizTag(quizId: Long, tagId: Long) {
        quizElasticRepository.findById(quizId).ifPresent {
            quizTagJpaRepository.findById(tagId).ifPresent { tag ->
                it.removeQuizTag(tag.name)
                quizElasticRepository.save(it)
            }
        }
    }


}