package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Test1;
import com.mycompany.myapp.repository.Test1Repository;
import com.mycompany.myapp.repository.search.Test1SearchRepository;
import com.mycompany.myapp.service.dto.Test1DTO;
import com.mycompany.myapp.service.mapper.Test1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Test1.
 */
@Service
@Transactional
public class Test1Service {

    private final Logger log = LoggerFactory.getLogger(Test1Service.class);

    private final Test1Repository test1Repository;

    private final Test1Mapper test1Mapper;

    private final Test1SearchRepository test1SearchRepository;

    public Test1Service(Test1Repository test1Repository, Test1Mapper test1Mapper, Test1SearchRepository test1SearchRepository) {
        this.test1Repository = test1Repository;
        this.test1Mapper = test1Mapper;
        this.test1SearchRepository = test1SearchRepository;
    }

    /**
     * Save a test1.
     *
     * @param test1DTO the entity to save
     * @return the persisted entity
     */
    public Test1DTO save(Test1DTO test1DTO) {
        log.debug("Request to save Test1 : {}", test1DTO);
        Test1 test1 = test1Mapper.toEntity(test1DTO);
        test1 = test1Repository.save(test1);
        Test1DTO result = test1Mapper.toDto(test1);
        test1SearchRepository.save(test1);
        return result;
    }

    /**
     * Get all the test1S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Test1DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Test1S");
        return test1Repository.findAll(pageable)
            .map(test1Mapper::toDto);
    }

    /**
     * Get one test1 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Test1DTO findOne(Long id) {
        log.debug("Request to get Test1 : {}", id);
        Test1 test1 = test1Repository.findOne(id);
        return test1Mapper.toDto(test1);
    }

    /**
     * Delete the test1 by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Test1 : {}", id);
        test1Repository.delete(id);
        test1SearchRepository.delete(id);
    }

    /**
     * Search for the test1 corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Test1DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Test1S for query {}", query);
        Page<Test1> result = test1SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(test1Mapper::toDto);
    }
}
