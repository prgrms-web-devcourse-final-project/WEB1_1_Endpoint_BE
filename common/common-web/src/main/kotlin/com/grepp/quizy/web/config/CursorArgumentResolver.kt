package com.grepp.quizy.web.config

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SortDirection
import com.grepp.quizy.common.dto.SortOrder
import com.grepp.quizy.web.annotation.CursorDefault
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import kotlin.math.min

class CursorArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.parameterType == Cursor::class.java
                && parameter.hasParameterAnnotation(CursorDefault::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val cursorDefault = getCursorDefault(parameter)
        val page = getPageParameter(webRequest, cursorDefault)
        val size = getSizeParameter(webRequest, cursorDefault)
        val sortOrders = createSortOrders(webRequest, cursorDefault)

        return Cursor(page, size, sortOrders)
    }

    private fun getCursorDefault(parameter: MethodParameter): CursorDefault =
        parameter.getParameterAnnotation(CursorDefault::class.java)
            ?: throw IllegalStateException("@CursorDefault annotation is required")

    private fun getPageParameter(webRequest: NativeWebRequest, cursorDefault: CursorDefault): Int =
        try {
            webRequest.getParameter(PAGE_PARAM)?.toInt() ?: cursorDefault.page
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid page parameter", e)
        }

    private fun getSizeParameter(webRequest: NativeWebRequest, cursorDefault: CursorDefault): Int =
        try {
            webRequest.getParameter(SIZE_PARAM)?.toInt() ?: cursorDefault.size
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid size parameter", e)
        }

    private fun createSortOrders(
        webRequest: NativeWebRequest,
        cursorDefault: CursorDefault
    ): List<SortOrder> {
        val fields = getSortFields(webRequest, cursorDefault)
        val directions = getSortDirections(webRequest, cursorDefault)
        return createSortOrdersList(fields, directions)
    }

    private fun getSortFields(webRequest: NativeWebRequest, cursorDefault: CursorDefault): Array<String> =
        webRequest.getParameter(SORT_FIELDS_PARAM)
            ?.split(",")
            ?.filter { it.isNotBlank() }
            ?.toTypedArray()
            ?: cursorDefault.sortFields.split(",").toTypedArray()

    private fun getSortDirections(webRequest: NativeWebRequest, cursorDefault: CursorDefault): Array<String> =
        webRequest.getParameter(SORT_DIRECTIONS_PARAM)
            ?.split(",")
            ?.filter { it.isNotBlank() }
            ?.toTypedArray()
            ?: cursorDefault.sortDirections.split(",").toTypedArray()

    private fun createSortOrdersList(fields: Array<String>, directions: Array<String>): List<SortOrder> =
        buildList {
            repeat(min(fields.size, directions.size)) { i ->
                try {
                    add(
                        SortOrder(
                            fields[i].trim(),
                            SortDirection.valueOf(directions[i].trim().uppercase())
                        )
                    )
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException("Invalid sort direction: ${directions[i]}", e)
                }
            }
        }

    companion object {
        private const val PAGE_PARAM = "page"
        private const val SIZE_PARAM = "size"
        private const val SORT_FIELDS_PARAM = "sortOrders[key]"
        private const val SORT_DIRECTIONS_PARAM = "sortOrders[direction]"
    }
}