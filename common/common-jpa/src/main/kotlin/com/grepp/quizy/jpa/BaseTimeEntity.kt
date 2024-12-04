package com.grepp.quizy.jpa

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTimeEntity (
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime
) {
    constructor() : this(LocalDateTime.now(), LocalDateTime.now())
}
