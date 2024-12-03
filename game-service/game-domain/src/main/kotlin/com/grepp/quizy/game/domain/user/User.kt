package com.grepp.quizy.game.domain.user

class User(
    val id: Long = 0,
    private var _name: String,
    private var _imgPath: String,
    val rating: Int = 1500,
) {
    val name: String
        get() = _name

    val imgPath: String
        get() = _imgPath

    fun updateUserInfo(name: String, imgPath: String) {
        this._name = name
        this._imgPath = imgPath
    }

}