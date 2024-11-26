package com.grepp.quizy.user.api.user

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.user.api.global.util.toImageFile
import com.grepp.quizy.user.api.user.dto.UpdateProfileRequest
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.domain.user.UserUpdateUseCase
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/user")
class UserApi(
    private val userUpdateUseCase: UserUpdateUseCase,
) {

    @PutMapping("/me")
    fun updateMe(
        @RequestPart("data", required = false) request: UpdateProfileRequest?,
        @RequestPart("profileImage", required = false) profileImage: MultipartFile?,
        @AuthUser principal: UserPrincipal,
    ): ApiResponse<Unit> {
        val image = profileImage?.let {
            toImageFile(it)
        }

        userUpdateUseCase.updateProfile(UserId(principal.value), request?.name, image)
        return ApiResponse.success()
    }

}