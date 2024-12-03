package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.quiz.domain.quiz.exception.QuizException
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizReader(private val quizRepository: QuizRepository) {
    fun read(id: QuizId): Quiz {
        return quizRepository.findById(id)
                ?: throw QuizException.NotFound
    }

    fun readAll(creatorId: UserId, cursor: Cursor): SliceResult<Quiz> {
        return quizRepository.findAllByCreatorId(creatorId, cursor)
    }

    fun readIn(ids: List<QuizId>): List<Quiz> {
        return quizRepository.findByIdIn(ids)
    }

    fun readWithLock(id: QuizId): Quiz {
        return quizRepository.findByIdWithLock(id)
                ?: throw QuizException.NotFound
    }

    fun readTags(ids: List<QuizTagId>): List<QuizTag> {
        return quizRepository.findTagsByInId(ids)
    }


    fun readCounts(ids: List<QuizId>) = quizRepository.findCountsByInId(ids)

}
