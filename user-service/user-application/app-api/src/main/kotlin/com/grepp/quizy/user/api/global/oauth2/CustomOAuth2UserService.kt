package com.grepp.quizy.user.api.global.oauth2

import com.grepp.quizy.user.domain.user.*
import com.grepp.quizy.user.domain.user.exception.CustomUserException
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
            e.printStackTrace()
            throw IllegalArgumentException()
        }
    }

    private fun findOrCreateUser(attributes: OAuth2Attributes) {
        val email = attributes.email
        val provider = attributes.provider

        try {
            val user = userReader.read(email)
            if (user.provider.provider != provider) {
                throw IllegalArgumentException("User already exists with email: $email")
                // TODO: 예외 처리하기
            }
        } catch (e: CustomUserException.UserNotFoundException) {
            createNewUser(attributes)
        }
    }

    private fun createNewUser(attributes: OAuth2Attributes) {
        val newUser = User(
            id = UserId(0),
            _userProfile = UserProfile(
                name = attributes.name,
                email = attributes.email,
                profileImageUrl = attributes.profileImageUrl
            ),
            provider = ProviderType(
                attributes.provider,
                attributes.snsId
            ),
            _role = Role.GUEST
        )
        userAppender.append(newUser)
    }
}