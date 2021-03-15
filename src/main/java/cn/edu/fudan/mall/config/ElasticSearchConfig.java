package cn.edu.fudan.mall.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import javax.annotation.PostConstruct;

@Configuration
public class ElasticSearchConfig {
    /**
     * 防止netty的bug
     * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Bean
    RestHighLevelClient elasticSearchClient(){
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo("nightbubble.top:9200")
                .build();
        RestHighLevelClient client = RestClients.create(configuration).rest();
        return client;
    }
}
