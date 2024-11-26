package com.grepp.quizy.user.domain.user

interface ProfileImageUploader {
    fun uploadFile(file: ImageFile, directory: String = "images"): String
}