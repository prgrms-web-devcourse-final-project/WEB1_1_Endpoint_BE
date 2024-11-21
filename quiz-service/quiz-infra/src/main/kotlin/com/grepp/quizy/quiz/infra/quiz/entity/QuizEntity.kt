package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.jpa.BaseTimeEntity
import com.grepp.quizy.quiz.domain.quiz.*
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize

@Entity
@Table(name = "quizzes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "quiz_type")
abstract class QuizEntity(
        val userId: Long,
        @Enumerated(EnumType.STRING) var category: QuizCategory,
        @Enumerated(EnumType.STRING) val type: QuizType,
        var content: String,
        @BatchSize(size = 100)
        @ManyToMany(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.MERGE],
        )
        @JoinTable(
                name = "quiz_tags_mapping",
                joinColumns = [JoinColumn(name = "quiz_id")],
                inverseJoinColumns = [JoinColumn(name = "tag_id")],
        )
        val tags: MutableSet<QuizTagEntity> = mutableSetOf(),
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(
                name = "quiz_options",
                joinColumns =
                        [
                                JoinColumn(
                                        name = "quiz_id",
                                        nullable = false,
                                )
                        ],
        )
        val options: MutableList<QuizOptionVO>,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
        val likeCount: Long = 0,
        var commentCount: Long = 0,
) : BaseTimeEntity() {

    abstract fun toDomain(): Quiz

    abstract fun update(quiz: Quiz): QuizEntity

    companion object {
        fun from(quiz: Quiz): QuizEntity {
            return when (quiz) {
                is ABTest -> ABTestEntity.from(quiz)
                is OXQuiz -> OXQuizEntity.from(quiz)
                is MultipleChoiceQuiz ->
                        MultipleChoiceQuizEntity.from(quiz)
                else ->
                        throw IllegalArgumentException(
                                "알 수 없는 퀴즈 타입입니다"
                        )
            }
        }
    }

    fun updateContent(content: QuizContent) {
        this.category = content.category
        this.content = content.content
        updateOptions(content.options)
        updateTags(content.tags.map { QuizTagEntity.from(it) })
    }

    private fun updateOptions(options: List<QuizOption>) {
        this.options.clear()
        this.options.addAll(options.map { QuizOptionVO.from(it) })
    }

    private fun updateTags(newTags: List<QuizTagEntity>) {
        val currentTags = this.tags.toSet()
        val newTagsSet = newTags.toSet()

        val tagsToAdd = newTagsSet.minus(currentTags)
        val tagsToRemove =
                this.tags
                        .filter {
                            it in currentTags.minus(newTagsSet)
                        }
                        .toSet()

        this.tags.addAll(tagsToAdd)
        this.tags.removeAll(tagsToRemove)
    }
}
