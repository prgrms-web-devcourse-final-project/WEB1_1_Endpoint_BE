package com.grepp.quizy.game.domain.useranswer

import com.grepp.quizy.game.domain.quiz.GameQuizAnswer
import com.grepp.quizy.game.domain.quiz.GameQuizOption
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class UserAnswerTest : BehaviorSpec({

    given("정적 팩토리 메서드 호출") {
        `when`("사용자 답변을 생성할 때") {
            val userAnswer =
                UserAnswer.from(
                    1,
                    1,
                    "OS는 운영체제의 약자이다",
                    "O",
                    GameQuizAnswer("O", "운영체제는 영어로 Operating System이기 때문에 약자가 맞다."),
                    true
                )
            then("사용자 답변이 생성되어야 한다.") {
                userAnswer.gameId shouldBe 1
                userAnswer.userId shouldBe 1
                userAnswer.quizContent shouldBe "OS는 운영체제의 약자이다"
                userAnswer.choice shouldBe "O"
                userAnswer.answer shouldBe "O"
                userAnswer.explanation shouldBe "운영체제는 영어로 Operating System이기 때문에 약자가 맞다."
                userAnswer.isCorrect shouldBe true
            }
        }
    }
})