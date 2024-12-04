package com.grepp.quizy.game.domain.user

class User(
    val id: Long = 0,
    private var _name: String,
    private var _imgPath: String,
    private var _rating: Int = 1500,
) {
    val name: String
        get() = _name

    val imgPath: String
        get() = _imgPath

    val rating: Int
        get() = _rating

    fun updateUserInfo(name: String, imgPath: String) {
        this._name = name
        this._imgPath = imgPath
    }

    fun updateRating(rating: Int) {
        this._rating = rating
    }

}