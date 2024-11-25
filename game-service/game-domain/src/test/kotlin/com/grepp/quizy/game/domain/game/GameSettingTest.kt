package com.grepp.quizy.game.domain.game

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameSettingTest() : DescribeSpec({
    describe("GameSetting") {
        context("게임 설정을 생성하면") {
            it("게임 설정의 기본 정보를 저장한다.") {
                val gameSetting = GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10)

                gameSetting.subject shouldBe GameSubject.JAVASCRIPT
                gameSetting.level shouldBe GameLevel.EASY
                gameSetting.quizCount shouldBe 10
            }
        }
        context("게임 설정을 변경하면") {
            it("주제를 변경한다.") {
                val gameSetting = GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10)

                val updatedGameSetting = gameSetting.updateSubject(GameSubject.SPRING)

                updatedGameSetting.subject shouldBe GameSubject.SPRING
            }
            it("난이도를 변경한다.") {
                val gameSetting = GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10)

                val updatedGameSetting = gameSetting.updateLevel(GameLevel.NORMAL)

                updatedGameSetting.level shouldBe GameLevel.NORMAL
            }
            it("문제 수를 변경한다.") {
                val gameSetting = GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10)

                val updatedGameSetting = gameSetting.updateQuizCount(20)

                updatedGameSetting.quizCount shouldBe 20
            }
        }
    }
}) {
}