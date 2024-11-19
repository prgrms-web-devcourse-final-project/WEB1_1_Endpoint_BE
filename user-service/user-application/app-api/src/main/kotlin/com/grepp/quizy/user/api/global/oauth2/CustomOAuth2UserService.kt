package com.grepp.quizy.user.api.global.oauth2

import com.grepp.quizy.domain.user.*
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val extractorFactory: OAuth2UserInfoExtractorFactory
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        return processOAuth2User(userRequest, oAuth2User)
    }

    private fun processOAuth2User(
        userRequest: OAuth2UserRequest,
        oAuth2User: OAuth2User
    ): OAuth2User {
        val registrationId = userRequest.clientRegistration.registrationId
        val extractor = extractorFactory.getExtractor(registrationId)

        return runCatching {
            val attributes = extractor.extract(oAuth2User.attributes)
            findOrCreateUser(attributes)
            CustomOAuth2User(attributes)
        }.getOrElse { e ->
            throw IllegalArgumentException(
//                when (e) {
//                    is IllegalArgumentException -> "Invalid OAuth2 provider"
//                    is EmailNotFoundExceptionException -> "Email not found from OAuth2 provider"
//                    else -> "Failed to process OAuth2 user"
//                }
            )
        }
    }

    private fun findOrCreateUser(attributes: OAuth2Attributes): User {
        val email = attributes.email

        return userReader.isExist(email).let {
            if (it) {
                throw IllegalArgumentException("User already exists with email: $email")
                // TODO: 예외 처리하기
            } else null
        } ?: createNewUser(attributes)
    }

    private fun createNewUser(attributes: OAuth2Attributes): User {
        val newUser = User(
            id = UserId(0),
            userProfile = UserProfile(
                name = attributes.name,
                email = attributes.email
            ),
            provider = ProviderType(attributes.provider, attributes.email),
            role = Role.USER
        )
        return userAppender.append(newUser)
    }
}