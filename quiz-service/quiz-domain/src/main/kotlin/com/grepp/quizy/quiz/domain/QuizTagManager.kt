package com.grepp.quizy.quiz.domain

import org.springframework.stereotype.Component

@Component
class QuizTagManager(
        private val quizRepository: QuizRepository
) {
    /**
     * 주어진 퀴즈 태그 목록을 준비합니다.
     *
     * @param tags 준비할 태그 목록
     * @return 기존 태그가 저장소에 있는 경우 해당 태그로 대체된 준비된 태그 목록
     * @throws IllegalArgumentException 제공된 태그 목록이 비어있는 경우
     */
    fun prepare(tags: List<QuizTag>): List<QuizTag> {
        assert(tags.isNotEmpty()) { "태그는 비어있을 수 없습니다" }

        val existingTagsByName =
                quizRepository
                        .findTagsByNameIn(
                                tags.map { it.name }
                        )
                        .associateBy { it.name }

        return tags.map { tag ->
            existingTagsByName[tag.name] ?: tag
        }
    }
}
