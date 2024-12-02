package com.grepp.quizy.jpa

import com.grepp.quizy.common.event.ServiceEvent
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(name = "outbox_events")
class OutboxEventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val eventType: String,
    val aggregateType: String,
    val aggregateId: Long,
    @JdbcTypeCode(SqlTypes.JSON)
    val payload: Map<String, Any>,
    val origin: String,
    val _origin: String = origin,
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun from(event: ServiceEvent): OutboxEventEntity {
            return OutboxEventEntity(
                eventType = event.eventType,
                aggregateType = event.aggregateType,
                aggregateId = event.aggregateId,
                payload = event.toPayload(),
                origin = event.getOrigin(),
                timestamp = event.timestamp
            )
        }
    }
}