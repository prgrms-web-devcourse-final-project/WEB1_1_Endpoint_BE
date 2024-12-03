package com.grepp.quizy.quiz.infra.log.repository

interface CustomLogSearchRepository {

    fun topKKeyword(k: Int): List<String>
}