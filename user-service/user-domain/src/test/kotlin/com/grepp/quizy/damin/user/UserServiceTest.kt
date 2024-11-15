package com.grepp.quizy.damin.user

import com.grepp.quizy.domain.user.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class UserServiceTest : DescribeSpec({
    // Mocks
    val userAppender = mockk<UserAppender>()
    val userReader = mockk<UserReader>()
    val userRemover = mockk<UserRemover>()

    // System Under Test
    val userService = UserService(userAppender, userReader, userRemover)

    // Test Data
    val userId = UserId(1)
    val email = "test@example.com"
    val userProfile = UserProfile(
        name = "Test User",
        email = email,
        profileImageUrl = "http://example.com/test.jpg"
    )
    val providerVO = ProviderType(
        provider = AuthProvider.KAKAO,
        providerId = "kakaoId"
    )
    val user = User(
        id = userId,
        userProfile = userProfile,
        provider = providerVO,
        role = Role.USER
    )

    beforeSpec {
        // MockK 초기화
        clearAllMocks()
    }

    afterSpec {
        // 테스트 종료 후 정리
        clearAllMocks()
    }

    describe("appendUser") {
        context("새로운 유저가 주어졌을 때") {
            // Context
            every { userAppender.appendUser(user) } returns user

            it("userAppender를 통해 유저를 추가한다") {
                // Execute
                val newUser = userService.appendUser(user)

                // Verify
                newUser shouldBe user
                verify(exactly = 1) { userAppender.appendUser(user) }
            }
        }
    }

    describe("getUser") {
        context("유저 ID가 주어졌을 때") {
            context("해당 ID의 유저가 존재하는 경우") {
                // Context
                every { userReader.getUserById(userId.value) } returns user

                it("userReader를 통해 유저를 조회하여 반환한다") {
                    // Execute
                    val result = userService.getUser(userId.value)

                    // Verify
                    result shouldBe user
                    verify(exactly = 1) { userReader.getUserById(userId.value) }
                }
            }

            context("해당 ID의 유저가 존재하지 않는 경우") {
                // Context
                val nonExistentId = 999L
                every { userReader.getUserById(nonExistentId) } throws RuntimeException()

                it("UserNotFoundException을 발생시킨다") {
                    // Execute & Verify
                    shouldThrow<RuntimeException> {
                        userService.getUser(nonExistentId)
                    }
                    verify(exactly = 1) { userReader.getUserById(nonExistentId) }
                }
            }
        }
    }

    describe("removeUser") {
        context("삭제할 유저가 주어졌을 때") {
            // Context
            every { userRemover.removeUser(user) } just runs

            it("userRemover를 통해 유저를 삭제한다") {
                // Execute
                userService.removeUser(user)

                // Verify
                verify(exactly = 1) { userRemover.removeUser(user) }
            }
        }
    }
}) {
}