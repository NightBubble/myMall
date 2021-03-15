package cn.edu.fudan.mall.nosql.elsaticsearch.repository;

import cn.edu.fudan.mall.nosql.elsaticsearch.pojo.EsProduct;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {

    Page<EsProduct> findByNameOrSubTitleOrKeywords(String str, Pageable page);
}
