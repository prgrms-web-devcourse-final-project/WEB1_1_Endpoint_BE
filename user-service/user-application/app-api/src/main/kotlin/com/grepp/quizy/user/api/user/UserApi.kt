package com.grepp.quizy.user.api.user

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.user.api.global.util.toImageFile
import com.grepp.quizy.user.api.user.dto.UpdateProfileRequest
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.domain.user.UserUpdateUseCase
import org.springframework.web.bind.annotation.*
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
        @RequestHeader("X-Auth-Id") userId: String,
    ): ApiResponse<Unit> {
        val image = profileImage?.let {
            toImageFile(it)
        }

        userUpdateUseCase.updateProfile(UserId(userId.toLong()), request?.name, image)
        return ApiResponse.success()
    }

}