package com.grepp.quizy.jpa

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
}
