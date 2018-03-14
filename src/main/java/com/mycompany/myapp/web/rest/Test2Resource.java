package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Test2Service;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Test2DTO;
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
 * REST controller for managing Test2.
 */
@RestController
@RequestMapping("/api")
public class Test2Resource {

    private final Logger log = LoggerFactory.getLogger(Test2Resource.class);

    private static final String ENTITY_NAME = "test2";

    private final Test2Service test2Service;

    public Test2Resource(Test2Service test2Service) {
        this.test2Service = test2Service;
    }

    /**
     * POST  /test-2-s : Create a new test2.
     *
     * @param test2DTO the test2DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new test2DTO, or with status 400 (Bad Request) if the test2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-2-s")
    @Timed
    public ResponseEntity<Test2DTO> createTest2(@Valid @RequestBody Test2DTO test2DTO) throws URISyntaxException {
        log.debug("REST request to save Test2 : {}", test2DTO);
        if (test2DTO.getId() != null) {
            throw new BadRequestAlertException("A new test2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Test2DTO result = test2Service.save(test2DTO);
        return ResponseEntity.created(new URI("/api/test-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-2-s : Updates an existing test2.
     *
     * @param test2DTO the test2DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated test2DTO,
     * or with status 400 (Bad Request) if the test2DTO is not valid,
     * or with status 500 (Internal Server Error) if the test2DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-2-s")
    @Timed
    public ResponseEntity<Test2DTO> updateTest2(@Valid @RequestBody Test2DTO test2DTO) throws URISyntaxException {
        log.debug("REST request to update Test2 : {}", test2DTO);
        if (test2DTO.getId() == null) {
            return createTest2(test2DTO);
        }
        Test2DTO result = test2Service.save(test2DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, test2DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /test-2-s : get all the test2S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of test2S in body
     */
    @GetMapping("/test-2-s")
    @Timed
    public ResponseEntity<List<Test2DTO>> getAllTest2S(Pageable pageable) {
        log.debug("REST request to get a page of Test2S");
        Page<Test2DTO> page = test2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/test-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /test-2-s/:id : get the "id" test2.
     *
     * @param id the id of the test2DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the test2DTO, or with status 404 (Not Found)
     */
    @GetMapping("/test-2-s/{id}")
    @Timed
    public ResponseEntity<Test2DTO> getTest2(@PathVariable Long id) {
        log.debug("REST request to get Test2 : {}", id);
        Test2DTO test2DTO = test2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(test2DTO));
    }

    /**
     * DELETE  /test-2-s/:id : delete the "id" test2.
     *
     * @param id the id of the test2DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteTest2(@PathVariable Long id) {
        log.debug("REST request to delete Test2 : {}", id);
        test2Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/test-2-s?query=:query : search for the test2 corresponding
     * to the query.
     *
     * @param query the query of the test2 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/test-2-s")
    @Timed
    public ResponseEntity<List<Test2DTO>> searchTest2S(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Test2S for query {}", query);
        Page<Test2DTO> page = test2Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/test-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
