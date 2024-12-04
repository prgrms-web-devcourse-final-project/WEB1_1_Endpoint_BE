package com.grepp.quizy.quiz.infra.user.repository

import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
    private val quizUserJpaRepository: QuizUserJpaRepository
) {

}