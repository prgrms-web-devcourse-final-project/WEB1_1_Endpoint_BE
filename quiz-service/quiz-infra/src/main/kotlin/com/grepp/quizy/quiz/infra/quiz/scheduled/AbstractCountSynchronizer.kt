package com.grepp.quizy.quiz.infra.quiz.scheduled


abstract class AbstractCountSynchronizer {

    companion object {
         const val SCHEDULED_FIXED_RATE = 10000L // 10초
    }

    abstract fun synchronize()
}