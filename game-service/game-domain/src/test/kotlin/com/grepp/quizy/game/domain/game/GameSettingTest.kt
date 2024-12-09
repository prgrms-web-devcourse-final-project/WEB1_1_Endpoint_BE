package com.grepp.quizy.game.domain.game

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class GameSettingTest : BehaviorSpec({

    given("게임 설정") {
        val setting = GameSetting(GameSubject.ALGORITHM, GameLevel.EASY, 5)
        `when`("주제를 변경하면") {
            val updatedSetting = setting.updateSubject(GameSubject.DATABASE)
            then("주제가 변경되어야 한다.") {
                updatedSetting.subject shouldBe GameSubject.DATABASE
            }
        }
        `when`("난이도를 변경하면") {
            val updatedSetting = setting.updateLevel(GameLevel.HARD)
            then("난이도가 변경되어야 한다.") {
                updatedSetting.level shouldBe GameLevel.HARD
            }
        }
        `when`("문제 수를 변경하면") {
            val updatedSetting = setting.updateQuizCount(10)
            then("문제 수가 변경되어야 한다.") {
                updatedSetting.quizCount shouldBe 10
            }
        }
    }

})