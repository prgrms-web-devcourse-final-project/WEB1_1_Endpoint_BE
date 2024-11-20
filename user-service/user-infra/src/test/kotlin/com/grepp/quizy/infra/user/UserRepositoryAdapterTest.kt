package com.grepp.quizy.infra.user

import com.grepp.quizy.user.domain.user.AuthProvider
import com.grepp.quizy.user.domain.user.Role
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.infra.user.entity.ProviderTypeVO
import com.grepp.quizy.user.infra.user.entity.UserEntity
import com.grepp.quizy.user.infra.user.entity.UserProfileVO
import com.grepp.quizy.user.infra.user.repository.UserJPARepository
import com.grepp.quizy.user.infra.user.repository.UserRepositoryAdaptor
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.test.context.ActiveProfiles
import java.util.*


@ActiveProfiles("test")
class UserRepositoryAdapterTest(
) : DescribeSpec({

    val userJPARepository: UserJPARepository = mockk<UserJPARepository>()
    val userRepositoryAdaptor = UserRepositoryAdaptor(userJPARepository)

    // Dependencies (Context)

    // Test Data (Input)
    val userId = UserId(1)
    val email = "test@example.com"

    val userProfile = UserProfileVO(
        name = "Test User",
        email = email,
        profileImageUrl = "http://example.com/testUser.jpg"
    )

    val providerVO = ProviderTypeVO(
        provider = AuthProvider.KAKAO,
        providerId = "kakaoId"
    )

    val userEntity = UserEntity(
        id = userId.value,
        userProfile = userProfile,
        provider = providerVO,
        role = Role.USER
    )

    val user = userEntity.toDomain()

    beforeSpec {
        // MockK 초기화
        clearAllMocks()
    }

    afterSpec {
        // 테스트 종료 후 정리
        clearAllMocks()
    }

    describe("findById 에서") {

        context("유저 ID가 주어졌을 때") {
            // Context
            every { userJPARepository.findById(userId.value) } returns Optional.of(userEntity)

            it("유저 도메인 객체를 반환한다") {
                // Execute
                val result = userRepositoryAdaptor.findById(userId.value)

                // Verify
                result!!.id shouldBe user.id
                verify(exactly = 1) { userJPARepository.findById(userId.value) }
            }
        }

    }

    describe("existsByEmail 에서") {
        context("해당 이메일을 가진 유저가 존재하는 경우") {
            // Context
            every { userJPARepository.existsByUserProfile_Email(email) } returns true

            it("true를 반환한다") {
                // Execute
                val result = userRepositoryAdaptor.existsByEmail(email)

                // Verify
                result shouldBe true
                verify(exactly = 1) { userJPARepository.existsByUserProfile_Email(email) }
            }
        }

        context("해당 이메일을 가진 유저가 존재하지 않는 경우") {
            // Context
            val nonExistentEmail = "nonexistent@example.com"
            every { userJPARepository.existsByUserProfile_Email(nonExistentEmail) } returns false

            it("false를 반환한다") {
                // Execute
                val result = userRepositoryAdaptor.existsByEmail(nonExistentEmail)

                // Verify
                result shouldBe false
                verify(exactly = 1) { userJPARepository.existsByUserProfile_Email(nonExistentEmail) }
            }
        }
    }

    describe("save 에서") {
        context("유저 도메인 객체가 주어졌을 때") {
            // Context
            every { userJPARepository.save(any()) } returns userEntity

            it("저장된 유저 도메인 객체를 반환한다") {
                // Execute
                val result = userRepositoryAdaptor.save(user)

                // Verify
                result.id shouldBe user.id
                verify(exactly = 1) { userJPARepository.save(any()) }
            }
        }
    }

    describe("delete") {
        context("유저 도메인 객체가 주어졌을 때") {
            // Context
            every { userJPARepository.delete(any()) } just runs

            it("유저를 삭제한다") {
                // Execute
                userRepositoryAdaptor.delete(user)

                // Verify
                verify(exactly = 1) { userJPARepository.delete(any()) }
            }
        }
    }
}) {
}