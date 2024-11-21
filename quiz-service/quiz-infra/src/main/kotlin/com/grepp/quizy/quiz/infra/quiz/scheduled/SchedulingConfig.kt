package com.grepp.quizy.quiz.infra.quiz.scheduled

import javax.sql.DataSource
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30M")
class SchedulingConfig(
        @Value("\${shedlock.name}")
        private val shedlockTableName: String
) {
    @Bean
    fun taskScheduler(): TaskScheduler {
        return ThreadPoolTaskScheduler().apply {
            poolSize = 1
            threadNamePrefix = "scheduled-task-pool-"
            setAwaitTerminationSeconds(30)
            setWaitForTasksToCompleteOnShutdown(true)
            initialize()
        }
    }

    @Bean
    fun lockProvider(dataSource: DataSource): LockProvider {
        return JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withTableName(shedlockTableName)
                        .withJdbcTemplate(JdbcTemplate(dataSource))
                        .usingDbTime()
                        .build()
        )
    }
}
