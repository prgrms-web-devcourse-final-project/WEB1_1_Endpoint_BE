package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.exception.GameException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameSubjectTest {

    @Test
    fun `게임 주제 생성`() {
        // given
        // when
        val subject = GameSubject.fromString("데이터베이스")
        // then
        assertThat(subject).isEqualTo(GameSubject.DATABASE)
    }

    @Test
    fun `찾을 수 없는 주제`() {
        assertThrows<GameException.GameSubjectNotFoundException> {
            GameSubject.fromString("아무거나")
        }
    }
}