package com.grepp.quizy.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface OutboxEventJpaRepository : JpaRepository<OutboxEventEntity, Long> {
}