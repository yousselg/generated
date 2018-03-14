package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Test1Service;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Test1DTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Test1.
 */
@RestController
@RequestMapping("/api")
public class Test1Resource {

    private final Logger log = LoggerFactory.getLogger(Test1Resource.class);

    private static final String ENTITY_NAME = "test1";

    private final Test1Service test1Service;

    public Test1Resource(Test1Service test1Service) {
        this.test1Service = test1Service;
    }

    /**
     * POST  /test-1-s : Create a new test1.
     *
     * @param test1DTO the test1DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new test1DTO, or with status 400 (Bad Request) if the test1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-1-s")
    @Timed
    public ResponseEntity<Test1DTO> createTest1(@Valid @RequestBody Test1DTO test1DTO) throws URISyntaxException {
        log.debug("REST request to save Test1 : {}", test1DTO);
        if (test1DTO.getId() != null) {
            throw new BadRequestAlertException("A new test1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Test1DTO result = test1Service.save(test1DTO);
        return ResponseEntity.created(new URI("/api/test-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-1-s : Updates an existing test1.
     *
     * @param test1DTO the test1DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated test1DTO,
     * or with status 400 (Bad Request) if the test1DTO is not valid,
     * or with status 500 (Internal Server Error) if the test1DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-1-s")
    @Timed
    public ResponseEntity<Test1DTO> updateTest1(@Valid @RequestBody Test1DTO test1DTO) throws URISyntaxException {
        log.debug("REST request to update Test1 : {}", test1DTO);
        if (test1DTO.getId() == null) {
            return createTest1(test1DTO);
        }
        Test1DTO result = test1Service.save(test1DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, test1DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /test-1-s : get all the test1S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of test1S in body
     */
    @GetMapping("/test-1-s")
    @Timed
    public ResponseEntity<List<Test1DTO>> getAllTest1S(Pageable pageable) {
        log.debug("REST request to get a page of Test1S");
        Page<Test1DTO> page = test1Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/test-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /test-1-s/:id : get the "id" test1.
     *
     * @param id the id of the test1DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the test1DTO, or with status 404 (Not Found)
     */
    @GetMapping("/test-1-s/{id}")
    @Timed
    public ResponseEntity<Test1DTO> getTest1(@PathVariable Long id) {
        log.debug("REST request to get Test1 : {}", id);
        Test1DTO test1DTO = test1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(test1DTO));
    }

    /**
     * DELETE  /test-1-s/:id : delete the "id" test1.
     *
     * @param id the id of the test1DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteTest1(@PathVariable Long id) {
        log.debug("REST request to delete Test1 : {}", id);
        test1Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/test-1-s?query=:query : search for the test1 corresponding
     * to the query.
     *
     * @param query the query of the test1 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/test-1-s")
    @Timed
    public ResponseEntity<List<Test1DTO>> searchTest1S(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Test1S for query {}", query);
        Page<Test1DTO> page = test1Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/test-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
