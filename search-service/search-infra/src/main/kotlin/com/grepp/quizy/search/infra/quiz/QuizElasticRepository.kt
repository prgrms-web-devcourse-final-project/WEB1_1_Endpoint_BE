package com.grepp.quizy.search.infra.quiz

import org.springframework.data.repository.Repository

interface QuizElasticRepository: Repository<QuizDocument, Long>