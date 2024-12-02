package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.quiz.QuizCategory

class QuizUser(
    val id: UserId = UserId(),
    private var _name: String,
    private var _imgPath: String,
    private val _interests: MutableList<QuizCategory> = mutableListOf()
) {
    val name: String
        get() = _name

    val imgPath: String
        get() = _imgPath

    val interests: List<QuizCategory>
        get() = _interests

    fun addInterests(interests: List<QuizCategory>) {
        _interests.clear()
        _interests.addAll(interests)
    }

    fun update(name: String, imgPath: String) {
        _name = name
        _imgPath = imgPath
    }
}