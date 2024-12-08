package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.useranswer.events.UserAnsweredEvent

class QuizUser(
    val id: UserId,
    private var profile: UserProfile = UserProfile("", ""),
    private val _interests: MutableList<QuizCategory> = mutableListOf(),
    private val _achievements: MutableList<QuizUserAchievement> = mutableListOf(),
    private var _stats: UserStats = UserStats(id)
) {
    val name: String
        get() = profile.name

    val imgPath: String
        get() = profile.imgPath

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

    fun update(profile: UserProfile) {
        this.profile = profile
    }

    private fun hasAchievement(achievementId: String): Boolean {
        return this._achievements.any { it.achievementId == achievementId }
    }

    private fun addAchievement(achievement: QuizUserAchievement) {
        this._achievements.add(achievement)
    }
}

data class UserProfile(
    val name: String,
    val imgPath: String
)