package com.grepp.quizy.jpa.utils

import com.grepp.quizy.common.dto.Cursor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

object PagingUtil {
    fun toPageRequest(cursor: Cursor): PageRequest =
        PageRequest.of(
            cursor.page,
            cursor.size,
            cursor.sortOrders
                .map { order ->
                    Sort.Order(
                        Sort.Direction.fromString(order.direction.name),
                        order.key
                    )
                }
                .let { Sort.by(it) }
        )
}