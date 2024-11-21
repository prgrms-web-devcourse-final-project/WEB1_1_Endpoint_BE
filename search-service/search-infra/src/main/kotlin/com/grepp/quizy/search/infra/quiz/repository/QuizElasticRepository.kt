package com.grepp.quizy.search.infra.quiz.repository

import com.grepp.quizy.search.infra.quiz.document.QuizDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface QuizElasticRepository :
        ElasticsearchRepository<QuizDocument, Long>,
        CustomQuizSearchRepository
