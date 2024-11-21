package com.grepp.quizy.search.infra.quiz.repository

import com.grepp.quizy.search.infra.quiz.document.UserAnswerDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface UserAnswerElasticRepository : ElasticsearchRepository<UserAnswerDocument, Long>
