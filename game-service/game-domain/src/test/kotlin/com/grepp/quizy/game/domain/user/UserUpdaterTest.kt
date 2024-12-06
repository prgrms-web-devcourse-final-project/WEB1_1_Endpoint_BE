package com.grepp.quizy.game.domain.user

import com.grepp.quizy.game.domain.exception.UserException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserUpdaterTest : BehaviorSpec({

    val userRepository = mockk<UserRepository>()
    val userUpdater = UserUpdater(userRepository)

    given("유저 정보 업데이트") {
        context("유저가 존재하는 경우") {
            every { userRepository.findById(1) } returns User(
                1,
                "나민혁",
                "https://cdn.example.com/v2/users/1/profile.webp"
            )
            every { userRepository.save(any()) } returnsArgument 0

            `when`("유저 정보를 업데이트 할 때") {
                val updatedUser = userUpdater.update(
                    User(1, "홍길동", "/users/avatars/1/profile.jpg")
                )

                then("유저 정보가 변경되어야 한다") {
                    updatedUser.name shouldBe "홍길동"
                    updatedUser.imgPath shouldBe "/users/avatars/1/profile.jpg"

                    verify {
                        userRepository.save(withArg {
                            it.name shouldBe "홍길동"
                            it.imgPath shouldBe "/users/avatars/1/profile.jpg"
                        })
                    }
                }
            }
            `when`("유저 레이팅을 업데이트 할 때") {
                userUpdater.updateRating(1, 1600)

                then("유저 레이팅이 변경되어야 한다") {
                    verify {
                        userRepository.save(withArg {
                            it.rating shouldBe 1600
                        })
                    }
                }
            }
        }

        context("유저가 존재하지 않는 경우") {
            every { userRepository.findById(999) } returns null

            `when`("유저 정보를 업데이트 하려고 하면") {
                then("UserNotFoundException이 발생해야 한다") {
                    shouldThrow<UserException.UserNotFoundException> {
                        userUpdater.update(User(999, "홍길동", "/users/avatars/999/profile.jpg"))
                    }
                }
            }
            `when`("유저 레이팅을 업데이트 하려고 하면") {
                then("UserNotFoundException이 발생해야 한다") {
                    shouldThrow<UserException.UserNotFoundException> {
                        userUpdater.updateRating(999, 1600)
                    }
                }
            }
        }
    }
})