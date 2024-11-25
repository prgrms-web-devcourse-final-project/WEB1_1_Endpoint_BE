package com.grepp.quizy.user.api.user

import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.domain.user.UserValidUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/internal/user")
class UserInternalApi(
    private val userValidUseCase: UserValidUseCase
) {

    @GetMapping("/validate/{userId}")
    fun validateUser(@PathVariable userId: Long): ResponseEntity<Unit> {
        return if (userValidUseCase.isValidUser(UserId(userId))) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}