package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Test2;
import com.mycompany.myapp.repository.Test2Repository;
import com.mycompany.myapp.repository.search.Test2SearchRepository;
import com.mycompany.myapp.service.dto.Test2DTO;
import com.mycompany.myapp.service.mapper.Test2Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Test2.
 */
@Service
@Transactional
public class Test2Service {

    private final Logger log = LoggerFactory.getLogger(Test2Service.class);

    private final Test2Repository test2Repository;

    private final Test2Mapper test2Mapper;

    private final Test2SearchRepository test2SearchRepository;

    public Test2Service(Test2Repository test2Repository, Test2Mapper test2Mapper, Test2SearchRepository test2SearchRepository) {
        this.test2Repository = test2Repository;
        this.test2Mapper = test2Mapper;
        this.test2SearchRepository = test2SearchRepository;
    }

    /**
     * Save a test2.
     *
     * @param test2DTO the entity to save
     * @return the persisted entity
     */
    public Test2DTO save(Test2DTO test2DTO) {
        log.debug("Request to save Test2 : {}", test2DTO);
        Test2 test2 = test2Mapper.toEntity(test2DTO);
        test2 = test2Repository.save(test2);
        Test2DTO result = test2Mapper.toDto(test2);
        test2SearchRepository.save(test2);
        return result;
    }

    /**
     * Get all the test2S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Test2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Test2S");
        return test2Repository.findAll(pageable)
            .map(test2Mapper::toDto);
    }

    /**
     * Get one test2 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Test2DTO findOne(Long id) {
        log.debug("Request to get Test2 : {}", id);
        Test2 test2 = test2Repository.findOne(id);
        return test2Mapper.toDto(test2);
    }

    /**
     * Delete the test2 by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Test2 : {}", id);
        test2Repository.delete(id);
        test2SearchRepository.delete(id);
    }

    /**
     * Search for the test2 corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Test2DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Test2S for query {}", query);
        Page<Test2> result = test2SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(test2Mapper::toDto);
    }
}
