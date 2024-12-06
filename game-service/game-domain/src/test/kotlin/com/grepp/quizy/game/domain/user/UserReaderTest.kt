package com.grepp.quizy.game.domain.user

import com.grepp.quizy.game.domain.exception.UserException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserReaderTest : BehaviorSpec({

    val userRepository = mockk<UserRepository>()
    val userReader = UserReader(userRepository)

    given("유저 조회") {
        val expectedUser = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        every { userRepository.findById(1) } returns expectedUser

        `when`("유저를 조회할 때") {
            val foundUser = userReader.read(1)

            then("유저가 조회되어야 한다.") {
                with(foundUser) {
                    id shouldBe 1
                    name shouldBe "나민혁"
                    imgPath shouldBe "https://cdn.example.com/v2/users/1/profile.webp"
                }
            }
        }
    }

    given("존재하지 않는 유저 조회") {
        every { userRepository.findById(999) } returns null

        `when`("유저를 조회할 때") {
            then("예외가 발생해야 한다.") {
                shouldThrow<UserException.UserNotFoundException> {
                    userReader.read(999)
                }
            }
        }
    }

    given("유저 목록 조회") {
        val expectedUsers = listOf(
            User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp"),
            User(2, "나민혁2", "https://cdn.example.com/v2/users/2/profile.webp")
        )
        every { userRepository.findByIdIn(listOf(1, 2)) } returns expectedUsers

        `when`("유저 목록을 조회할 때") {
            val foundUsers = userReader.readIn(listOf(1, 2))

            then("유저 목록이 조회되어야 한다.") {
                foundUsers shouldBe expectedUsers
            }
        }
    }

    given("일부 유저만 존재하는 경우의 목록 조회") {
        val existingUsers = listOf(
            User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp"),
            User(2, "김철수", "https://cdn.example.com/v2/users/2/profile.webp")
        )

        every { userRepository.findByIdIn(listOf(1L, 2L, 999L)) } returns existingUsers

        `when`("유저 목록을 조회할 때") {
            then("예외가 발생해야 한다.") {
                shouldThrow<UserException.UserNotFoundException> {
                    userReader.readIn(listOf(1L, 2L, 999L))
                }
            }
        }
    }

    given("유저 레이팅 조회") {
        every { userRepository.findRatingById(1) } returns 100

        `when`("유저 레이팅을 조회할 때") {
            val rating = userReader.readRating(1)

            then("유저 레이팅이 조회되어야 한다") {
                rating shouldBe 100
            }
        }
    }

    given("존재하지 않는 유저 레이팅 조회") {
        every { userRepository.findRatingById(999) } returns null

        `when`("유저 레이팅을 조회할 때") {
            then("예외가 발생해야 한다.") {
                shouldThrow<UserException.UserNotFoundException> {
                    userReader.readRating(999)
                }
            }
        }
    }

})