package com.grepp.quizy.user.api.user

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.user.api.global.util.toImageFile
import com.grepp.quizy.user.api.user.dto.UpdateProfileRequest
import com.grepp.quizy.user.api.user.dto.UserResponse
import com.grepp.quizy.user.domain.user.UserDeleteUseCase
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.domain.user.UserReadUseCase
import com.grepp.quizy.user.domain.user.UserUpdateUseCase
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/user")
class UserApi(
    private val userReadUseCase: UserReadUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
    private val userDeleteUseCase: UserDeleteUseCase
) {
    @GetMapping("/me")
    fun getMe(
        @AuthUser principal: UserPrincipal,
    ): ApiResponse<UserResponse> {
        return ApiResponse.success(UserResponse.from(userReadUseCase.getUser(UserId(principal.value))))
    }

    @GetMapping("/info/{userId}")
    fun getUser(
        @PathVariable userId: Long,
    ): ApiResponse<UserResponse> {
        return ApiResponse.success(UserResponse.from(userReadUseCase.getUser(UserId(userId))))
    }

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

    @DeleteMapping("/me")
    fun deleteMe(
        @AuthUser principal: UserPrincipal,
    ): ApiResponse<Unit> {
        userDeleteUseCase.removeUser(UserId(principal.value))
        return ApiResponse.success()
    }

}