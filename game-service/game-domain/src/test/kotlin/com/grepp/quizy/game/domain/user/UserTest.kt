package com.grepp.quizy.game.domain.user

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class UserTest: BehaviorSpec(
    {
        given("유저") {
            `when`("생성할 때") {
                val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
                then("레이팅은 기본값이 지정되어야 한다.") {
                    user.rating shouldBe  1500
                }
            }
        }
        given("User") {
            val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp",1500)
            `when`("레이팅을 업데이트할 때") {
                user.updateRating(1600)
                then("레이팅이 업데이트 되어야 한다.") {
                    user.rating shouldBe 1600
                }
            }
            `when`("이름을 변경하면") {
                user.updateUserInfo("홍길동", "https://cdn.example.com/v2/users/1/profile.webp")
                then("이름이 변경 되어야 한다.") {
                    user.name shouldBe "홍길동"
                }
            }
            `when`("이미지를 변경하면") {
                user.updateUserInfo("나민혁", "https://cdn.example.com/v2/users/1/profile2.webp")
                then("이미지가 변경되어야 한다.") {
                    user.imgPath shouldBe "https://cdn.example.com/v2/users/1/profile2.webp"
                }
            }
            `when`("정보를 모두 변경하면") {
                user.updateUserInfo("최고민혁","/users/avatars/1/profile.jpg")
                then("이름과 이미지가 변경되어야 한다.") {
                    user.name shouldBe "최고민혁"
                    user.imgPath shouldBe "/users/avatars/1/profile.jpg"
                }
            }
        }
    }
) {

}