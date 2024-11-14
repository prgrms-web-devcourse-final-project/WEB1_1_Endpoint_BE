package com.grepp.quizy.infra.quiz.entity

import com.grepp.quizy.domain.quiz.Quiz
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class QuizEntity(@Id val id: Long = 0, val title: String) {
    companion object {
        fun from(quiz: Quiz): QuizEntity {
            return QuizEntity(title = quiz.title)
        }
    }

    fun toDomain(): Quiz {
        return Quiz(id = id, title = title)
    }
}
