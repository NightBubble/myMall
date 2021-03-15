package cn.edu.fudan.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@SpringBootApplication()
@EnableElasticsearchRepositories
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
