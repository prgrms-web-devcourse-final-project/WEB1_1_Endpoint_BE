package com.grepp.quizy.search.infra.quiz

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface QuizElasticRepository : ElasticsearchRepository<QuizDocument, Long>, CustomQuizSearchRepository