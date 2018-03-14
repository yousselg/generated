package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Test1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Test1 entity.
 */
public interface Test1SearchRepository extends ElasticsearchRepository<Test1, Long> {
}
