package com.grepp.quizy.quiz.infra.quizread.repository

import com.grepp.quizy.quiz.infra.quizread.document.UserAnswerDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface UserAnswerElasticRepository :
        ElasticsearchRepository<UserAnswerDocument, Long>
