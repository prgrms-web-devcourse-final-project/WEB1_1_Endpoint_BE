package com.grepp.quizy.quiz.infra.log.repository

import com.grepp.quizy.quiz.infra.log.document.LogDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface LogElasticRepository :
    ElasticsearchRepository<LogDocument, String>,
    CustomLogSearchRepository