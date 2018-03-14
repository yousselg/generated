package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Test2;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Test2 entity.
 */
public interface Test2SearchRepository extends ElasticsearchRepository<Test2, Long> {
}
