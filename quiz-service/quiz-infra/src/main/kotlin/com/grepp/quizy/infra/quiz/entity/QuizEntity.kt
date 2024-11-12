package com.grepp.quizy.infra.quiz.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class QuizEntity(var title: String = "") {

    @Id var id: Long = 0

    companion object {
        fun from(title: String): QuizEntity {
            return QuizEntity(title)
        }
    }
}
