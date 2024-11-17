package com.grepp.quizy.quiz.infra.quiz.entity

import QuizContentVO
import QuizOptionVO
import com.grepp.quizy.quiz.domain.Quiz
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize

@Entity
@Table(name = "quizzes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "quiz_type")
abstract class BaseQuizEntity(
        @Embedded val content: QuizContentVO,
        @BatchSize(size = 100)
        @ManyToMany(
                fetch = FetchType.LAZY,
                cascade =
                        [
                                CascadeType.PERSIST,
                                CascadeType.REMOVE,
                        ],
        )
        @JoinTable(
                name = "quiz_tags_mapping",
                joinColumns =
                        [JoinColumn(name = "quiz_id")],
                inverseJoinColumns =
                        [JoinColumn(name = "tag_id")],
        )
        val tags: Set<QuizTagEntity> = setOf(),
        @ElementCollection
        @CollectionTable(
                name = "quiz_options",
                joinColumns = [JoinColumn(name = "quiz_id")],
        )
        val options: List<QuizOptionVO>,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
) {
    abstract fun toDomain(): Quiz
}
