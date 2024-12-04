package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.user.QuizUser
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.user.UserStats
import jakarta.persistence.*

@Entity
@Table(name = "users")
class QuizUserEntity(
    @Id
    val id: Long = 0,
    var name: String,
    var imgPath: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_interests",
        joinColumns = [JoinColumn(name = "id")]
    )
    val interests: MutableList<QuizUserInterestVO> = mutableListOf(),
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_achievements",
        joinColumns = [JoinColumn(name = "id")]
    )
    val achievements: MutableList<QuizUserAchievementVO> = mutableListOf(),
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id")
    val stats: UserStatsEntity = UserStatsEntity(id)
) {

    companion object {
        fun from(quizUser: QuizUser): QuizUserEntity {
            return QuizUserEntity(
                id = quizUser.id.value,
                name = quizUser.name,
                imgPath = quizUser.imgPath,
                interests = quizUser.interests.map { QuizUserInterestVO(it) }.toMutableList(),
                achievements = quizUser.achievements.map { QuizUserAchievementVO.from(it) }.toMutableList(),
                stats = UserStatsEntity.from(quizUser.stats)
            )
        }
    }

    fun toDomain(): QuizUser {
        return QuizUser(
            id = UserId(id),
            _name = name,
            _imgPath = imgPath,
            _interests = interests.map { it.interest }.toMutableList(),
            _achievements = achievements.map { it.toDomain() }.toMutableList(),
            _stats = stats.toDomain()
        )
    }

    fun update(name: String, imgPath: String) {
        this.name = name
        this.imgPath = imgPath
    }
}