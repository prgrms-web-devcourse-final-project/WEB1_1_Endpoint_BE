package com.grepp.quizy.game.domain.user

import com.grepp.quizy.game.domain.exception.UserException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UserReaderTest : DescribeSpec({

    val userRepository = FakeUserRepository()
    val userReader = UserReader(userRepository)

    beforeTest {

    }

    afterTest {
        userRepository.clear()
    }

    describe("UserReader") {
        context("id를 통해 조회하면") {
            it("유저를 조회한다.") {
                val user1 = User(name = "민혁", imgPath = "imgPath")
                val user2 = User(name = "minhyeok", imgPath = "imgPath123")
                val savedUsers = userRepository.saveAll(listOf(user1, user2))

                val foundUser = userReader.read(savedUsers[0].id)

                foundUser.id shouldBe savedUsers[0].id
                foundUser.name shouldBe "민혁"
                foundUser.imgPath shouldBe "imgPath"
                foundUser.rating shouldBe 1500

            }
            context("유저가 없다면") {
                it("예외를 반환한다.") {
                    val user1 = User(name = "민혁", imgPath = "imgPath")
                    val user2 = User(name = "minhyeok", imgPath = "imgPath123")
                    val savedUsers = userRepository.saveAll(listOf(user1, user2))

                    shouldThrow<UserException.UserNotFoundException> {
                        userReader.read(-1)
                    }
                }
            }
        }
        context("id 목록을 통해 조회하면") {
            it("유저 목록을 조회한다.") {
                val user1 = User(name = "민혁", imgPath = "imgPath")
                val user2 = User(name = "minhyeok", imgPath = "imgPath123")

                val savedUsers = userRepository.saveAll(listOf(user1, user2))

                val foundUser = userReader.readIn(listOf(savedUsers[0].id, savedUsers[1].id))

                foundUser.size shouldBe 2
                foundUser[0].id shouldBe savedUsers[0].id
                foundUser[0].name shouldBe "민혁"
                foundUser[0].imgPath shouldBe "imgPath"
                foundUser[0].rating shouldBe 1500
                foundUser[1].id shouldBe savedUsers[1].id
                foundUser[1].name shouldBe "minhyeok"
                foundUser[1].imgPath shouldBe "imgPath123"
                foundUser[1].rating shouldBe 1500
            }
            context("해당 유저가 없다면") {
                it("예외를 반환한다.") {

                    val user1 = User(name = "민혁", imgPath = "imgPath")
                    val user2 = User(name = "minhyeok", imgPath = "imgPath123")

                    val savedUsers = userRepository.saveAll(listOf(user1, user2))

                    shouldThrow<UserException.UserNotFoundException> {
                        userReader.readIn(listOf(savedUsers[0].id, savedUsers[1].id, savedUsers[1].id + 1))
                    }
                }
            }
        }
    }
}) {
}