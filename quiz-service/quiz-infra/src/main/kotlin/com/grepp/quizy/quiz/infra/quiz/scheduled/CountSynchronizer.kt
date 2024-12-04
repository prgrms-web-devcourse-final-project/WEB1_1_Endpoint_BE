package com.grepp.quizy.quiz.infra.quiz.scheduled


interface CountSynchronizer {

    companion object {
         const val SCHEDULED_FIXED_RATE = 10000L // 10초
    }

    fun synchronize()
}