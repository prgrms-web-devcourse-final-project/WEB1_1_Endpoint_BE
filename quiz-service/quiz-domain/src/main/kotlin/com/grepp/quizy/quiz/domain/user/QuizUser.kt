package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.useranswer.events.UserAnsweredEvent

class QuizUser(
    val id: UserId = UserId(),
    private var _name: String,
    private var _imgPath: String,
    private val _interests: MutableList<QuizCategory> = mutableListOf(),
    private val _achievements: MutableList<QuizUserAchievement> = mutableListOf(),
    private var _stats: UserStats = UserStats(id)
) {
    val name: String
        get() = _name

    val imgPath: String
        get() = _imgPath

    val interests: List<QuizCategory>
        get() = _interests

    val achievements: List<QuizUserAchievement>
        get() = _achievements

    val stats: UserStats
        get() = _stats

    fun applyAnsweredEvent(event: UserAnsweredEvent) {
        _stats = _stats.update(event)
        Achievement::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .filter { !hasAchievement(it.id) }
            .filter { it.isAchieved(_stats) }
            .onEach { addAchievement(QuizUserAchievement.from(it.id)) }
    }

    fun addInterests(interests: List<QuizCategory>) {
        _interests.clear()
        _interests.addAll(interests)
    }

    fun update(name: String, imgPath: String) {
        _name = name
        _imgPath = imgPath
    }

    private fun hasAchievement(achievementId: String): Boolean {
        return this._achievements.any { it.achievementId == achievementId }
    }

    private fun addAchievement(achievement: QuizUserAchievement) {
        this._achievements.add(achievement)
    }
}