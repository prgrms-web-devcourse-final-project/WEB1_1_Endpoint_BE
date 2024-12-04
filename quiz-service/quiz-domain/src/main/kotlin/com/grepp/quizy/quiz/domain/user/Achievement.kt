package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty

sealed class Achievement(
    val id: String,
    val title: String,
    val description: String,
) {
    abstract fun isAchieved(userStats: UserStats): Boolean

    companion object {

        private val achievements by lazy {
            Achievement::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .associateBy { it.id }
        }

        fun getAllAchievements() = achievements.values.toList()
        fun getById(id: String) = achievements[id]
    }

    data object QuizBeginner : Achievement(
        id = "QUIZ_BEGINNER",
        title = "시작이 반이다",
        description = "첫 문제를 풀어보세요",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.totalAnswered >= 1
    }

    data object FirstCorrectAnswer : Achievement(
        id = "FIRST_CORRECT",
        title = "첫 정답",
        description = "처음으로 문제를 맞혔어요",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.totalCorrect >= 1
    }

    data object QuizEnthusiast : Achievement(
        id = "QUIZ_ENTHUSIAST",
        title = "퀴즈 애호가",
        description = "100문제 이상 풀기",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.totalAnswered >= 100
    }

    data object StrikeMaster : Achievement(
        id = "STRIKE_MASTER",
        title = "연속 정답왕",
        description = "10문제 연속 정답",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.consecutiveCorrectAnswers >= 10
    }

    data object QuizMaster : Achievement(
        id = "QUIZ_MASTER",
        title = "퀴즈 마스터",
        description = "1000문제 이상 풀기",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.totalAnswered >= 1000
    }

    data object Perfectionist : Achievement(
        id = "PERFECTIONIST",
        title = "완벽주의자",
        description = "100문제 이상 풀고 정답률 95% 이상 달성",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.totalAnswered >= 100 && userStats.correctRate >= 0.95
    }

    object DailyChallenger : Achievement(
        id = "DAILY_CHALLENGER",
        title = "꾸준한 도전자",
        description = "7일 연속 퀴즈 풀기",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.dailyStreak >= 7
    }

    data object DedicatedLearner : Achievement(
        id = "DEDICATED_LEARNER",
        title = "성실한 학습자",
        description = "30일 연속 퀴즈 풀기",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.dailyStreak >= 30
    }

    data object CategoryExplorer : Achievement(
        id = "CATEGORY_EXPLORER",
        title = "분야 탐험가",
        description = "모든 카테고리에서 최소 10문제 이상 풀기",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            userStats.categoryStats.values.all { it.answered >= 10 }
    }

    data object ChallengeSeeker : Achievement(
        id = "CHALLENGE_SEEKER",
        title = "도전자",
        description = "어려운 난이도 문제 50개 이상 정답",
    ) {
        override fun isAchieved(userStats: UserStats): Boolean =
            (userStats.difficultyStats[QuizDifficulty.HARD]?.completed ?: 0) >= 50
    }
}