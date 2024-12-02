package com.grepp.quizy.quiz.api.useranswer

import com.grepp.quizy.quiz.domain.useranswer.UserStats
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/internal/user-answers")
class UserAnswerInternalApi {

    @GetMapping("/performance")
    fun getStatistics(
        @AuthUser userPrincipal: UserPrincipal
    ): UserStats? {
        return null
    }
}