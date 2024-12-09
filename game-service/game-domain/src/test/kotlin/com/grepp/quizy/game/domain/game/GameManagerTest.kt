package com.grepp.quizy.game.domain.game

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*

class GameManagerTest : BehaviorSpec({
    val gameRepository = mockk<GameRepository>()
    val gameManager = GameManager(gameRepository)

    given("게임 매니저에서") {
        beforeTest {
            every { gameRepository.deleteById(any()) } just Runs
        }

        `when`("게임을 삭제할 때") {
            val gameId = 1L
            gameManager.destroy(gameId)

            then("repository의 deleteById가 정확한 ID와 함께 호출되어야 한다") {
                verify(exactly = 1) { gameRepository.deleteById(gameId) }
            }
        }
    }
})