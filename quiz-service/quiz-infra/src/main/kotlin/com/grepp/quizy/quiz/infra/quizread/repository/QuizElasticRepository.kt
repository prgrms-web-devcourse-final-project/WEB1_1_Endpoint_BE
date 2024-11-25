package com.grepp.quizy.quiz.infra.quizread.repository

import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface QuizElasticRepository :
        ElasticsearchRepository<QuizDocument, Long>,
        CustomQuizSearchRepository
