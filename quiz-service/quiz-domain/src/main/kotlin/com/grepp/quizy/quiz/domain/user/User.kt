package com.grepp.quizy.quiz.domain.user

class User(
    val id: UserId = UserId(),
    private var _name: String,
    private var _imgPath: String,
) {
    val name: String
        get() = _name

    val imgPath: String
        get() = _imgPath

    fun update(name: String, imgPath: String) {
        _name = name
        _imgPath = imgPath
    }
}