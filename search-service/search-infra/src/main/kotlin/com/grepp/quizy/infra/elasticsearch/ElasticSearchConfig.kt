package com.grepp.quizy.infra.elasticsearch

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories(basePackages = ["com.grepp.quizy.infra"])
class ElasticSearchConfig: ElasticsearchConfiguration() {

    @Value("\${elasticsearch.host}")
    private lateinit var host: String

    override fun clientConfiguration(): ClientConfiguration =
        ClientConfiguration.builder()
            .connectedTo(host)
            .build()
}