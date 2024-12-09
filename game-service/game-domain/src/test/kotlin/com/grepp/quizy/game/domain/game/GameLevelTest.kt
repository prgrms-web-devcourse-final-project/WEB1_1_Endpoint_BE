package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.exception.GameException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GameLevelTest {

    @Test
    fun `게임 레벨 생성`() {
        // given
        // when
        val level = GameLevel.fromString("하")
        // then
        Assertions.assertThat(level).isEqualTo(GameLevel.EASY)
    }

    @Test
    fun `찾을 수 없는 레벨`() {
        Assertions.assertThatThrownBy {
            GameLevel.fromString("아무거나")
        }.isInstanceOf(GameException.GameLevelNotFoundException::class.java)
    }

}