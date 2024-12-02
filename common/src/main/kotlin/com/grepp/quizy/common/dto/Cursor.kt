package com.grepp.quizy.common.dto

data class Cursor(
    val page: Int = 0,
    val size: Int = 10,
    val sortOrders: List<SortOrder> = listOf(SortOrder("createdAt", SortDirection.DESC))
)
