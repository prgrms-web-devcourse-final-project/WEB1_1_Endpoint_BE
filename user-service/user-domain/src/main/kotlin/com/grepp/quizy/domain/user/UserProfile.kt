package com.grepp.quizy.domain.user

data class UserProfile(
    val name: String,
    val email: String,
    val profileImageUrl: String = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" // TODO : S3 기본 썸네일 등록하기
) {

}