package com.grepp.quizy.user.api.user.dto

import jakarta.validation.constraints.Size

data class UpdateProfileRequest(
    @field:Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    val name: String?,
) {
    constructor() : this(null)
}