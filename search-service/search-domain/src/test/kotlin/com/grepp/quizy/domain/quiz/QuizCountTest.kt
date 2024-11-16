package com.grepp.quizy.domain.quiz

import com.grepp.quizy.search.domain.quiz.QuizCommentCount
import com.grepp.quizy.search.domain.quiz.QuizLikeCount
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class QuizLikeCountTest : FunSpec({

    test("좋아요 수가 양수면 QuizLikeCount 생성한다.") {
        val likeCount = QuizLikeCount(10)
        likeCount.value shouldBe 10
    }

    test("좋아요 수가 음수면 IllegalArgumentException 발생한다.") {
        shouldThrow<IllegalArgumentException> {
            QuizLikeCount(-1)
        }
    }
})

class QuizCommentCountTest : FunSpec({

    test("댓글 수가 양수면 QuizCommentCount 생성한다.") {
        val likeCount = QuizCommentCount(10)
        likeCount.value shouldBe 10
    }

    test("댓글 수가 음수면 IllegalArgumentException 발생한다.") {
        shouldThrow<IllegalArgumentException> {
            QuizCommentCount(-1)
        }
    }
})