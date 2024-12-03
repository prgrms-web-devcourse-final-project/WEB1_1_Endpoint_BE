package com.grepp.quizy.web.annotation

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CursorDefault(
    val page: Int = 0,
    val size: Int = 10,
    val sortFields: String = "createdAt",
    val sortDirections: String = "DESC"
)